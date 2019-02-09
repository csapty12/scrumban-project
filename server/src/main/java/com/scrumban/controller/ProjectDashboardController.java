package com.scrumban.controller;

import com.scrumban.model.SwimLane;
import com.scrumban.model.Tickets;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.service.project.ProjectTicketService;
import com.scrumban.service.project.SwimLaneService;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
@Slf4j
public class ProjectDashboardController {

    private ValidationErrorService validationErrorService;
    private SwimLaneService swimLaneService;
    private ProjectTicketService projectTicketService;
    private ProjectService projectService;

    public ProjectDashboardController(ValidationErrorService validationErrorService, SwimLaneService swimLaneService, ProjectTicketService projectTicketService, ProjectService projectService) {
        this.validationErrorService = validationErrorService;
        this.swimLaneService = swimLaneService;
        this.projectTicketService = projectTicketService;
        this.projectService = projectService;
    }

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getAllTickets(@PathVariable String projectIdentifier) {

        Tickets allTicketsForProject = projectTicketService.getProjectDashboard(projectIdentifier);
        return new ResponseEntity<>(allTicketsForProject, HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> AddSwimLaneToProject(@PathVariable String projectIdentifier,
                                                  @Valid @RequestBody SwimLaneEntity swimLaneEntity,
                                                  BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }

        ProjectEntity updatedProjectEntity = swimLaneService.addSwimLaneToProject(projectIdentifier, swimLaneEntity);
        Tickets allTicketsForProject = projectTicketService.getProjectDashboard(projectIdentifier);
        System.out.println("swimalne order: " + allTicketsForProject.getSwimLaneOrder());
        return new ResponseEntity<>(allTicketsForProject, HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}/{swimLaneId}")
    public ResponseEntity<?> addTicketToSwimLane(@PathVariable String projectIdentifier,
                                                 @PathVariable String swimLaneId,
                                                 @Valid @RequestBody ProjectTicket projectTicket,BindingResult bindingResult) {

        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        LinkedHashMap<String, ProjectTicket> projectTicket1 = projectTicketService.addProjectTicketToProject(projectIdentifier, swimLaneId, projectTicket);
        return new ResponseEntity<>( projectTicket1,HttpStatus.OK);
    }

    @DeleteMapping("/{projectIdentifier}/{id}")
    public ResponseEntity<?> removeTicketFromProject(@PathVariable String projectIdentifier, @PathVariable Long id, @Valid @RequestBody ProjectTicket projectTicket){
        projectTicketService.removeTicketFromProject(projectTicket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{projectIdentifier}/{swimLaneId}")
    public ResponseEntity<?> updateSwimLaneTicketOrder(@PathVariable String projectIdentifier,
                                                 @Valid @RequestBody SwimLane swimLane, BindingResult bindingResult) {

        projectTicketService.updateTicketOrderForSwimLane(projectIdentifier, swimLane);
    return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{projectIdentifier}")
    public ResponseEntity<?> updateSwimLanes(@PathVariable String projectIdentifier, @Valid @RequestBody List<SwimLane> swimLanes, BindingResult bindingResult){
        System.out.println("project id: " + projectIdentifier);

        projectTicketService.updateTicketSwimLane(projectIdentifier, swimLanes);

        projectTicketService.updateTicketPositionInNewSwimLane(swimLanes);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
