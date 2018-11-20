package com.scrumban.controller;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.Project;
import com.scrumban.service.ProjectService;
import com.scrumban.service.ValidationErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {

    private ProjectService projectService;
    private ValidationErrorService validationErrorService;

    public ProjectController(ProjectService projectService, ValidationErrorService validationErrorService) {
        this.projectService = projectService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);

        if (errorMap != null) {
            return errorMap;
        }
        Project theProject = projectService.saveOrUpdate(project);
        return new ResponseEntity<>(theProject, HttpStatus.CREATED);
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        projectIdentifier = projectIdentifier.toUpperCase();
        Project project = projectService.getProject(projectIdentifier);
        if (project != null) {
            return new ResponseEntity<>(project, HttpStatus.FOUND);
        }
        throw new ProjectIdException("no project found with identifier: " + projectIdentifier);
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        Iterable<Project> allProjects = projectService.findAllProjects();
        return new ResponseEntity<>(allProjects, HttpStatus.OK);

    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier) {
        projectIdentifier = projectIdentifier.toUpperCase();
        Project project = projectService.getProject(projectIdentifier);
        if(project == null){
            throw new ProjectIdException("No Project with ID: " + projectIdentifier + " found");
        }
        projectService.deleteProject(projectIdentifier);
        return new ResponseEntity<>("Project with ID: " + projectIdentifier + " successfully deleted", HttpStatus.OK);
    }
}
