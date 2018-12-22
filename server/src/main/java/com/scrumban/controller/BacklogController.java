package com.scrumban.controller;

import com.scrumban.model.ProjectTask;
import com.scrumban.model.Tasks;
import com.scrumban.service.ProjectTaskService;
import com.scrumban.service.ValidationErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.lang.String.format;

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
        ResponseEntity<?> errorMap = validateJson(bindingResult);
        if (errorMap != null) {
            System.out.println("errormap: " + errorMap);
            return errorMap;
        }

        ProjectTask projectTask = projectTaskService.addProjectTask(backlogId, newProjectTask);
        return new ResponseEntity<>(projectTask, HttpStatus.CREATED);
    }

    @GetMapping("/{backlogId}")
    public ResponseEntity<?> getProjectTasksFromBacklog(@PathVariable String backlogId){
        System.out.println("backlog id here: "+ backlogId);
        Tasks projectTasks = projectTaskService.getProjectTasksFromBacklog(backlogId);
        return new ResponseEntity<>(projectTasks, HttpStatus.OK);
    }

    @GetMapping("/{backlogId}/{ticketNumber}")
    public ResponseEntity<?> getSingleProjectTask(@PathVariable String backlogId, @PathVariable String ticketNumber){
        System.out.println("backlogID: " + backlogId);
        System.out.println("ticketNumber: " + ticketNumber);
        ProjectTask projectTask = projectTaskService.getProjectTaskFromProjectSequence(backlogId,ticketNumber);
        return new ResponseEntity<>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlogId}/{ticketNumber}")
    public ResponseEntity<?> updateTicketInBacklog(@Valid @RequestBody ProjectTask projectTask, BindingResult bindingResult,
                                           @PathVariable String backlogId, @PathVariable String ticketNumber){

        ResponseEntity<?> errorMap = validateJson(bindingResult);
        if (errorMap != null) return errorMap;

        ProjectTask updatedProjectTask = projectTaskService.updateProjectTask(backlogId, ticketNumber, projectTask);
        return new ResponseEntity<>(updatedProjectTask, HttpStatus.OK);
    }

    @DeleteMapping("/{backlogId}/{ticketNumber}")
    public ResponseEntity<?> deleteTicketFromBacklog(
                                                 @PathVariable String backlogId, @PathVariable String ticketNumber){
        projectTaskService.deleteTicketFromBacklog(backlogId, ticketNumber);

        return new ResponseEntity<>(format("Ticket %s from backlog %s has been deleted", ticketNumber, backlogId), HttpStatus.OK);

    }

    private ResponseEntity<?> validateJson(BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);

        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        return null;
    }
}
