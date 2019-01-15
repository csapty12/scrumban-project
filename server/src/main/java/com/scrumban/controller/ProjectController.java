package com.scrumban.controller;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.project.Project;
import com.scrumban.service.project.ProjectService;
import com.scrumban.service.ValidationErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;

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
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        Project theProject = projectService.saveProject(project);
        Iterable<Project> allProjects = projectService.findAllProjects();
        return new ResponseEntity<>(allProjects, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        Project theProject = projectService.updateProject(project);
        return new ResponseEntity<>(theProject, HttpStatus.OK);
    }


    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        System.out.println("project identifer: "  + projectIdentifier);
        projectIdentifier = projectIdentifier.toUpperCase();
        Project project = projectService.tryToFindProject(projectIdentifier);

        if (project != null) {

            return new ResponseEntity<>(project, HttpStatus.OK);
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
        projectService.deleteProject(projectIdentifier);
        return new ResponseEntity<>("Project with ID: " + projectIdentifier + " successfully deleted", HttpStatus.OK);
    }
}
