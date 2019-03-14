package com.scrumban.validator;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.service.project.ProjectService;
import com.scrumban.service.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserProjectValidator {

    private UserService userService;
    private ProjectService projectService;

    public UserProjectValidator(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }

    public ProjectEntity getUserProject(String projectIdentifier, String userEmail) {
        User user = userService.getUser(userEmail);
        Optional<ProjectEntity> projectEntity = projectService.getProject(projectIdentifier, user);
        if (!projectEntity.isPresent()) {
            throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + " not found");
        }
        return projectEntity.get();
    }
}
