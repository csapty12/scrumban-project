package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectTicketRepository projectTicketRepository;
    private UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectTicketRepository projectTicketRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.projectTicketRepository = projectTicketRepository;
        this.userRepository = userRepository;
    }

    public ProjectEntity saveProject(ProjectEntity projectEntity, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            projectEntity.setUser(user.get());
            projectEntity.setProjectLeader(user.get().getFirstName() +" "+ user.get().getLastName());
            Optional<ProjectEntity> foundProject = tryToFindProject(projectEntity.getProjectIdentifier().toUpperCase(), userEmail);

            log.info("attempting to save project: {}", projectEntity.getProjectName());
            if (!foundProject.isPresent()) {
                List<SwimLaneEntity> swimLaneEntitySet = new LinkedList<>();
                projectEntity.setSwimLaneEntities(swimLaneEntitySet);
                ProjectEntity savedNewProject = projectRepository.save(projectEntity);
                log.info("new project: {} has been saved", savedNewProject.getProjectName());
                return savedNewProject;
            }
            throw new ProjectIdentifierException("project ID: " + projectEntity.getProjectIdentifier() + " already exists!");
        }
        throw new UsernameNotFoundException("User not found");

    }

    public ProjectEntity updateProject(ProjectEntity projectEntity, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            Optional<ProjectEntity> foundProjectEntity = tryToFindProject(projectEntity.getProjectIdentifier(), userEmail);
            if (foundProjectEntity.isPresent()) {
                projectEntity.setProjectLeader(foundProjectEntity.get().getProjectLeader());
                projectEntity.setUser(foundProjectEntity.get().getUser());
                return projectRepository.save(projectEntity);
            }
            throw new ProjectIdentifierException("projectEntity ID: " + projectEntity.getProjectIdentifier() + " not found!");
        }
        throw new UsernameNotFoundException("User not found");

    }

    public Iterable<ProjectEntity> findAllProjects(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            List<ProjectEntity> allProjects = projectRepository.findAllByUser(user.get());

            if (allProjects.size()!=0) {
                return allProjects;
            }
            throw new ProjectNotFoundException("No projects found");
        }
        throw new UsernameNotFoundException("No user found");

    }

    public void deleteProject(String projectIdentifier, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            Optional<ProjectEntity> projectEntity = tryToFindProject(projectIdentifier, userEmail);
            if (!projectEntity.isPresent()) {
                throw new ProjectIdentifierException("projectEntity ID: " + projectIdentifier.toUpperCase() + " does not exist!");
            }
            deleteProject(projectEntity);
        }
        else{
            throw new UsernameNotFoundException("No user found");
        }



    }

    public Optional<ProjectEntity> tryToFindProject(String projectIdentifier, String userEmail) {
        System.out.println("project identifier: " + projectIdentifier);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            Optional<ProjectEntity> project = projectRepository.findProjectEntityByProjectIdentifier(projectIdentifier);
            if(!project.isPresent()){
               return Optional.empty();
            }
            if(!project.get().getUser().getId().equals(user.get().getId())){
                throw new ProjectNotFoundException("User not associated wih project");
            }
            return project;
        }
        throw new UsernameNotFoundException("No user found");

    }
    private void deleteProject(Optional<ProjectEntity> projectEntity) {
        deleteAllProjectTickets(projectEntity);
        projectRepository.delete(projectEntity.get());
    }

    private void deleteAllProjectTickets(Optional<ProjectEntity> projectEntity) {
        projectEntity.ifPresent(project -> project.getProjectTickets().forEach(projectTicket -> projectTicketRepository.deleteProjectTicket(projectTicket.getId())));
    }
}