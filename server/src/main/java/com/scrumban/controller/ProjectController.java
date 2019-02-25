package com.scrumban.controller;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
@Slf4j
public class ProjectController {

    private ProjectService projectService;
    private ValidationErrorService validationErrorService;

    public ProjectController(ProjectService projectService, ValidationErrorService validationErrorService) {
        this.projectService = projectService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectEntity projectEntity, BindingResult bindingResult, Authentication authentication) {

        ResponseEntity<?> validationErrors = validateIncomingRequest(bindingResult);
        if (validationErrors != null) return validationErrors;
        User principal = (User) authentication.getPrincipal();
        ProjectEntity newProject = projectService.saveProject(projectEntity, principal.getEmail());
        return new ResponseEntity<>(newProject, HttpStatus.OK);
    }


    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByProjectIdentifier(@PathVariable String projectIdentifier, Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        projectIdentifier = projectIdentifier.toUpperCase();
        Optional<ProjectEntity> project = projectService.tryToFindProject(projectIdentifier, principal.getEmail());

        if (project.isPresent()) { return new ResponseEntity<>(project, HttpStatus.OK); }

        throw new ProjectIdentifierException("No project found with identifier: " + projectIdentifier);
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        Iterable<ProjectEntity> allProjects = projectService.findAllProjects(principal.getEmail());

        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectEntity projectEntity, Authentication authentication,  BindingResult bindingResult) {
        ResponseEntity<?> validationErrors = validateIncomingRequest(bindingResult);
        if (validationErrors != null) return validationErrors;
        User principal = (User) authentication.getPrincipal();
        ProjectEntity updatedProject = projectService.updateProject(projectEntity, principal.getEmail());
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier, Authentication authentication) {
        projectIdentifier = projectIdentifier.toUpperCase();
        User principal = (User) authentication.getPrincipal();
        projectService.deleteProject(projectIdentifier, principal.getEmail());
        return new ResponseEntity<>("Project deleted", HttpStatus.OK);
    }

    private ResponseEntity<?> validateIncomingRequest(BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        return null;
    }
}
