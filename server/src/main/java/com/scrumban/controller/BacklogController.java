package com.scrumban.controller;

import com.scrumban.model.ProjectTask;
import com.scrumban.service.ProjectTaskService;
import com.scrumban.service.ValidationErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private ProjectTaskService projectTaskService;
    private ValidationErrorService validationErrorService;

    public BacklogController(ProjectTaskService projectTaskService, ValidationErrorService validationErrorService) {
        this.projectTaskService = projectTaskService;
        this.validationErrorService = validationErrorService;
    }

    @PostMapping("/{backlogId}")
    public ResponseEntity<?> addProjectTaskToBacklog(@Valid @RequestBody ProjectTask newProjectTask,
                                                     BindingResult bindingResult,
                                                     @PathVariable String backlogId) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);

        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }

        ProjectTask projectTask = projectTaskService.addProjectTask(backlogId, newProjectTask);
        return new ResponseEntity<>(projectTask, HttpStatus.CREATED);
    }
}
