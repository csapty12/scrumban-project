package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.Project;
import com.scrumban.model.domain.User;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import com.scrumban.repository.domain.ProjectRepository;
import com.scrumban.repository.entity.ProjectTicketEntityRepository;
import com.scrumban.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService{

    private ProjectRepository projectRepository;
    private ProjectTicketEntityRepository projectTicketEntityRepository;
    private UserService userService;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectTicketEntityRepository projectTicketEntityRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.projectTicketEntityRepository = projectTicketEntityRepository;
        this.userService = userService;
    }

    @Override
    public Project saveProject(Project newProject, String userEmail) {
        User user = userService.getUser(userEmail);
        Optional<Project> existingProject = getProject(newProject.getProjectIdentifier(), user);


        if (existingProject.isPresent()) {
            log.error("Unable to save new project: {}", newProject.getProjectName());
            throw new ProjectIdentifierException("project ID: " + newProject.getProjectIdentifier() + " already exists!");
        }

        log.info("attempting to save project: {}", newProject.getProjectName());
        List<SwimLaneEntity> swimLaneEntityList = new LinkedList<>();
        newProject.setUser(user);
        newProject.setProjectLeader(createProjectLeader(user));
        newProject.setSwimLaneEntities(swimLaneEntityList);
        Project savedNewProject = projectRepository.save(newProject);
        log.info("new project: {} has been saved", savedNewProject.getProjectName());
        return savedNewProject;
    }

    @Override
    public Iterable<Project> findAllProjects(String userEmail) {
        User user = userService.getUser(userEmail);

        List<Project> allProjects = projectRepository.findAllByUser(user);

        if (allProjects.size() != 0) {
            return allProjects;
        }
        throw new ProjectNotFoundException("No projects found.");
    }
//
//    public ProjectEntity updateProject(ProjectEntity projectEntity, String userEmail) {
//        User user = userService.getUser(userEmail);
//
//        Optional<ProjectEntity> foundProjectEntity = getProject(projectEntity.getProjectIdentifier(), user);
//        if (!foundProjectEntity.isPresent()) {
//            throw new ProjectIdentifierException("projectEntity ID: " + projectEntity.getProjectIdentifier() + " not found!");
//        }
//
//        projectEntity.setProjectLeader(foundProjectEntity.get().getProjectLeader());
//        projectEntity.setUser(foundProjectEntity.get().getUser());
//        return projectEntityRepository.save(projectEntity);
//    }
//
//
//    public void deleteProject(String projectIdentifier, String userEmail) {
//        User user = userService.getUser(userEmail);
//
//        Optional<ProjectEntity> projectEntity = getProject(projectIdentifier, user);
//        if (!projectEntity.isPresent()) {
//            throw new ProjectIdentifierException("projectEntity ID: " + projectIdentifier + " does not exist!");
//        }
//        deleteProject(projectEntity);
//    }

    public Optional<Project> getProject(String projectIdentifier, User user) {
        Optional<Project> project = projectRepository.findProjectEntityByProjectIdentifier(projectIdentifier);
        if (project.isPresent()) {
            if (!isUserAssociatedWithProject(user, project.get())) {
                throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + " cannot be found.");
            }
        }
        return project;
    }

    protected boolean isUserAssociatedWithProject(User user, Project project) {
        return project.getUser().getId().equals(user.getId());
    }

    private String createProjectLeader(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

//    private void deleteProject(Optional<ProjectEntity> projectEntity) {
//        deleteAllProjectTickets(projectEntity);
//        projectEntityRepository.delete(projectEntity.get());
//    }

//    private void deleteAllProjectTickets(Optional<ProjectEntity> projectEntity) {
//        projectEntity.ifPresent(project -> project.getProjectTicketEntities().forEach(projectTicket -> projectTicketEntityRepository.deleteProjectTicket(projectTicket.getId())));
//    }
}