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
        User user = getUser(userEmail);
        Optional<ProjectEntity> project = getProject(projectEntity.getProjectIdentifier(), user);

        log.info("attempting to save project: {}", projectEntity.getProjectName());

        if (project.isPresent()) {
            throw new ProjectIdentifierException("project ID: " + projectEntity.getProjectIdentifier() + " already exists!");
        }

        List<SwimLaneEntity> swimLaneEntitySet = new LinkedList<>();
        projectEntity.setUser(user);
        projectEntity.setProjectLeader(createProjectLeader(user));
        projectEntity.setSwimLaneEntities(swimLaneEntitySet);
        ProjectEntity savedNewProject = projectRepository.save(projectEntity);
        log.info("new project: {} has been saved", savedNewProject.getProjectName());
        return savedNewProject;
    }

    public Iterable<ProjectEntity> findAllProjects(String userEmail) {
        User user = getUser(userEmail);

        List<ProjectEntity> allProjects = projectRepository.findAllByUser(user);

        if (allProjects.size() != 0) {
            return allProjects;
        }
        throw new ProjectNotFoundException("No projects found");
    }

    public ProjectEntity updateProject(ProjectEntity projectEntity, String userEmail) {
        User user = getUser(userEmail);

        Optional<ProjectEntity> foundProjectEntity = getProject(projectEntity.getProjectIdentifier(), user);
        if (foundProjectEntity.isPresent()) {
            projectEntity.setProjectLeader(foundProjectEntity.get().getProjectLeader());
            projectEntity.setUser(foundProjectEntity.get().getUser());
            return projectRepository.save(projectEntity);
        }
        throw new ProjectIdentifierException("projectEntity ID: " + projectEntity.getProjectIdentifier() + " not found!");
    }

    public void deleteProject(String projectIdentifier, String userEmail) {
        User user = getUser(userEmail);

        Optional<ProjectEntity> projectEntity = getProject(projectIdentifier, user);
        if (!projectEntity.isPresent()) {
            throw new ProjectIdentifierException("projectEntity ID: " + projectIdentifier.toUpperCase() + " does not exist!");
        }
        deleteProject(projectEntity);
    }


    private User getUser(String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User not found.");
        }
        return user.get();
    }

    //new
    private Optional<ProjectEntity> getProject(String projectIdentifier, User user) {
        System.out.println("project identifier: " + projectIdentifier);

        Optional<ProjectEntity> project = projectRepository.findProjectEntityByProjectIdentifier(projectIdentifier);
        if (project.isPresent()) {
            if (!isUserAssociatedWithProject(user, project.get())) {
                throw new ProjectNotFoundException("User not associated wih project");
            }
        }
        return project;
    }

    //old
    public Optional<ProjectEntity> getProject(String projectIdentifier, String userEmail) {
        System.out.println("project identifier: " + projectIdentifier);
        Optional<User> user = userRepository.findByEmail(userEmail);
        if (user.isPresent()) {
            Optional<ProjectEntity> project = projectRepository.findProjectEntityByProjectIdentifier(projectIdentifier);
            if (!project.isPresent()) {
                return Optional.empty();
            }
            if (!isUserAssociatedWithProject(user.get(), project.get())) {
                throw new ProjectNotFoundException("User not associated wih project");
            }
            return project;
        }
        throw new UsernameNotFoundException("No user found");
    }

    private boolean isUserAssociatedWithProject(User user, ProjectEntity project) {
        return project.getUser().getId().equals(user.getId());
    }

    private String createProjectLeader(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    private void deleteProject(Optional<ProjectEntity> projectEntity) {
        deleteAllProjectTickets(projectEntity);
        projectRepository.delete(projectEntity.get());
    }

    private void deleteAllProjectTickets(Optional<ProjectEntity> projectEntity) {
        projectEntity.ifPresent(project -> project.getProjectTickets().forEach(projectTicket -> projectTicketRepository.deleteProjectTicket(projectTicket.getId())));
    }
}