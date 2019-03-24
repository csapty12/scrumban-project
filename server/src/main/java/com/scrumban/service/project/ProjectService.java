package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.User;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import com.scrumban.repository.entity.ProjectEntityRepository;
import com.scrumban.repository.entity.ProjectTicketEntityRepository;
import com.scrumban.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectService {

    private ProjectEntityRepository projectEntityRepository;
    private ProjectTicketEntityRepository projectTicketEntityRepository;
    private UserService userService;


    public ProjectService(ProjectEntityRepository projectEntityRepository, ProjectTicketEntityRepository projectTicketEntityRepository, UserService userService) {
        this.projectEntityRepository = projectEntityRepository;
        this.projectTicketEntityRepository = projectTicketEntityRepository;
        this.userService = userService;
    }

    public ProjectEntity saveProject(ProjectEntity projectEntity, String userEmail) {
        User user = userService.getUser(userEmail);
        Optional<ProjectEntity> project = getProject(projectEntity.getProjectIdentifier(), user);


        if (project.isPresent()) {
            log.error("Unable to save new project: {}", projectEntity.getProjectName());
            throw new ProjectIdentifierException("project ID: " + projectEntity.getProjectIdentifier() + " already exists!");
        }

        log.info("attempting to save project: {}", projectEntity.getProjectName());
        List<SwimLaneEntity> swimLaneEntityList = new LinkedList<>();
        projectEntity.setUser(user);
        projectEntity.setProjectLeader(createProjectLeader(user));
        projectEntity.setSwimLaneEntities(swimLaneEntityList);
        ProjectEntity savedNewProject = projectEntityRepository.save(projectEntity);
        log.info("new project: {} has been saved", savedNewProject.getProjectName());
        return savedNewProject;
    }

    public Iterable<ProjectEntity> findAllProjects(String userEmail) {
        User user = userService.getUser(userEmail);

        List<ProjectEntity> allProjects = projectEntityRepository.findAllByUser(user);

        if (allProjects.size() != 0) {
            return allProjects;
        }
        throw new ProjectNotFoundException("No projects found.");
    }

    public ProjectEntity updateProject(ProjectEntity projectEntity, String userEmail) {
        User user = userService.getUser(userEmail);

        Optional<ProjectEntity> foundProjectEntity = getProject(projectEntity.getProjectIdentifier(), user);
        if (!foundProjectEntity.isPresent()) {
            throw new ProjectIdentifierException("projectEntity ID: " + projectEntity.getProjectIdentifier() + " not found!");
        }

        projectEntity.setProjectLeader(foundProjectEntity.get().getProjectLeader());
        projectEntity.setUser(foundProjectEntity.get().getUser());
        return projectEntityRepository.save(projectEntity);
    }


    public void deleteProject(String projectIdentifier, String userEmail) {
        User user = userService.getUser(userEmail);

        Optional<ProjectEntity> projectEntity = getProject(projectIdentifier, user);
        if (!projectEntity.isPresent()) {
            throw new ProjectIdentifierException("projectEntity ID: " + projectIdentifier + " does not exist!");
        }
        deleteProject(projectEntity);
    }

    public Optional<ProjectEntity> getProject(String projectIdentifier, User user) {
        Optional<ProjectEntity> project = projectEntityRepository.findProjectEntityByProjectIdentifier(projectIdentifier);
        if (project.isPresent()) {
            if (!isUserAssociatedWithProject(user, project.get())) {
                throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + " cannot be found.");
            }
        }
        return project;
    }

    protected boolean isUserAssociatedWithProject(User user, ProjectEntity project) {
        return project.getUser().getId().equals(user.getId());
    }

    private String createProjectLeader(User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

    private void deleteProject(Optional<ProjectEntity> projectEntity) {
        deleteAllProjectTickets(projectEntity);
        projectEntityRepository.delete(projectEntity.get());
    }

    private void deleteAllProjectTickets(Optional<ProjectEntity> projectEntity) {
        projectEntity.ifPresent(project -> project.getProjectTicketEntities().forEach(projectTicket -> projectTicketEntityRepository.deleteProjectTicket(projectTicket.getId())));
    }
}