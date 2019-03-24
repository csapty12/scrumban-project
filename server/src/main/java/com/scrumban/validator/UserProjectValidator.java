package com.scrumban.validator;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.Project;
import com.scrumban.model.domain.User;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.service.project.ProjectServiceImpl;
import com.scrumban.service.user.UserService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserProjectValidator {

    private UserService userService;
    private ProjectServiceImpl projectServiceImpl;

    public UserProjectValidator(UserService userService, ProjectServiceImpl projectServiceImpl) {
        this.userService = userService;
        this.projectServiceImpl = projectServiceImpl;
    }

    public Project getUserProject(String projectIdentifier, String userEmail) {
        User user = userService.getUser(userEmail);
        Optional<Project> projectEntity = projectServiceImpl.getProject(projectIdentifier, user);
        if (!projectEntity.isPresent()) {
            throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + " not found");
        }
        return projectEntity.get();
    }
}
