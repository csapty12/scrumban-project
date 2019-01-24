package com.scrumban.controller;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

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
    public ResponseEntity<?> createProject(@Valid @RequestBody ProjectEntity projectEntity, BindingResult bindingResult) throws ParseException {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        ProjectEntity theProjectEntity = projectService.saveProject(projectEntity);
        return new ResponseEntity<>(theProjectEntity, HttpStatus.OK);
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


    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectIdentifier) {
        System.out.println("projectEntity identifer: "  + projectIdentifier);
        projectIdentifier = projectIdentifier.toUpperCase();
        ProjectEntity projectEntity = projectService.tryToFindProject(projectIdentifier);

        if (projectEntity != null) {

            return new ResponseEntity<>(projectEntity, HttpStatus.OK);
        }
        throw new ProjectIdException("no projectEntity found with identifier: " + projectIdentifier);
    }

    @GetMapping
    public ResponseEntity<?> getAllProjects() {
        Iterable<ProjectEntity> allProjects = projectService.findAllProjects();
        return new ResponseEntity<>(allProjects, HttpStatus.OK);

    }

    @DeleteMapping("/{projectIdentifier}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectIdentifier) {
        projectIdentifier = projectIdentifier.toUpperCase();
        projectService.deleteProject(projectIdentifier);
        return new ResponseEntity<>("ProjectEntity with ID: " + projectIdentifier + " successfully deleted", HttpStatus.OK);
    }
}
