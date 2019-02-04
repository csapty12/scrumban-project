package com.scrumban.controller;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectEntity projectEntity, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        ProjectEntity theProjectEntity = projectService.saveProject(projectEntity);
        return new ResponseEntity<>(theProjectEntity, HttpStatus.OK);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        projectIdentifier = projectIdentifier.toUpperCase();
        Optional<ProjectEntity> projectEntity = projectService.tryToFindProject(projectIdentifier);

        if (projectEntity.isPresent()) {

            return new ResponseEntity<>(projectEntity, HttpStatus.OK);
        }
        throw new ProjectIdentifierException("no projectEntity found with identifier: " + projectIdentifier);
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        Iterable<ProjectEntity> allProjects = projectService.findAllProjects();
        return new ResponseEntity<>(allProjects, HttpStatus.OK);

    }

    @PatchMapping
    public ResponseEntity<?> updateProject(@Valid @RequestBody ProjectEntity projectEntity, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }

        ProjectEntity theProjectEntity = projectService.updateProject(projectEntity);
        return new ResponseEntity<>(theProjectEntity, HttpStatus.OK);
    }

    @DeleteMapping("/{projectIdentifier}")
    public void deleteProject(@PathVariable String projectIdentifier) {
        projectIdentifier = projectIdentifier.toUpperCase();
        projectService.deleteProject(projectIdentifier);

    }
}
