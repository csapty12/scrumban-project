package com.scrumban.controller;

import com.scrumban.model.Tickets;
import com.scrumban.model.project.Project;
import com.scrumban.model.project.ProjectTicket;
import com.scrumban.model.project.SwimLane;
import com.scrumban.service.ProjectTicketService;
import com.scrumban.service.SwimLaneService;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

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

        System.out.println("inside get all tickets: " + projectIdentifier);
        Tickets allTicketsForProject = projectTicketService.getProjectDashboard(projectIdentifier);
        return new ResponseEntity<>(allTicketsForProject, HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}")
    public ResponseEntity<?> AddSwimLaneToProject(@PathVariable String projectIdentifier,
                                                  @Valid @RequestBody SwimLane swimLane,
                                                  BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
//        System.out.println("swimlane: " + swimLane.getName());
//        System.out.println("inside add swimlane to project: " + projectIdentifier);
        Project updatedProject = swimLaneService.addSwimLaneToProject(projectIdentifier, swimLane);
        Tickets allTicketsForProject = projectTicketService.getProjectDashboard(projectIdentifier);
        System.out.println("swimalne order: " + allTicketsForProject.getSwimLaneOrder());
        return new ResponseEntity<>(allTicketsForProject, HttpStatus.OK);
    }

    @PostMapping("/{projectIdentifier}/{swimLaneId}")
    public ResponseEntity<?> addTicketToSwimLane(@PathVariable String projectIdentifier,
                                                 @PathVariable String swimLaneId,
                                                 @Valid @RequestBody ProjectTicket projectTicket) {

        System.out.println("projectIdentifier: " + projectIdentifier);
        System.out.println("swimLaneId: " + swimLaneId);
        System.out.println("projectTicket: " + projectTicket);

        ProjectTicket projectTicket1 = projectTicketService.addProjectTicketToProject(projectIdentifier, swimLaneId, projectTicket);
        String swimlaneName = projectTicket1.getSwimLane().getName();
        Map<String, ProjectTicket> projectTicketSwimLane= new HashMap<>();
        projectTicketSwimLane.put(swimlaneName,projectTicket1 );
        return new ResponseEntity<>(projectTicketSwimLane, HttpStatus.OK);
    }

    @DeleteMapping("/{projectIdentifier}/{id}")
    public ResponseEntity<?> removeTicketFromProject(@PathVariable String projectIdentifier, @PathVariable Long id, @Valid @RequestBody ProjectTicket projectTicket){
        System.out.println("id: " + id);
        System.out.println("projectID: " + projectIdentifier);

        projectTicketService.removeTicketFromProject(projectTicket);
        Tickets allTicketsForProject = projectTicketService.getProjectDashboard(projectIdentifier);
        return new ResponseEntity<>(allTicketsForProject,  HttpStatus.OK);
    }
}
