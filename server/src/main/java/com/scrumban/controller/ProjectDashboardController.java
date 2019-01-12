package com.scrumban.controller;

import com.scrumban.model.project.Project;
import com.scrumban.model.project.SwimLane;
import com.scrumban.service.SwimLaneService;
import com.scrumban.service.ValidationErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
@Slf4j
public class ProjectDashboardController {

    private ValidationErrorService validationErrorService;
    private SwimLaneService swimLaneService;

    public ProjectDashboardController(ValidationErrorService validationErrorService, SwimLaneService swimLaneService) {
        this.validationErrorService = validationErrorService;
        this.swimLaneService = swimLaneService;
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getAllTickets(@PathVariable String projectIdentifier){

        System.out.println("inside get all tickets: " + projectIdentifier);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> AddSwimLaneToProject( @PathVariable String projectIdentifier, @Valid @RequestBody SwimLane swimLane, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        System.out.println("swimlane: " + swimLane.getName());
        System.out.println("inside add swimlane to project: " + projectIdentifier);
        Project updatedProject = swimLaneService.addSwimLaneToProject(projectIdentifier, swimLane );
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }
}
