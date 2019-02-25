package com.scrumban.controller;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.SwimLane;
import com.scrumban.model.Tickets;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.service.project.ProjectTicketService;
import com.scrumban.service.project.SwimLaneService;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.project.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(value = "/{projectIdentifier}", produces =  MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAllTickets(@PathVariable String projectIdentifier, Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        Optional<ProjectEntity> project = projectService.tryToFindProject(projectIdentifier, principal.getEmail());
        if (project.isPresent()) {
            Tickets allTicketsForProject = projectTicketService.getProjectDashboard(project.get(), principal.getEmail());
            return new ResponseEntity<>(allTicketsForProject, HttpStatus.OK);
        }
        throw new ProjectNotFoundException("No project found with identifier: " + projectIdentifier);

    }

    @PostMapping(value = "/{projectIdentifier}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> AddSwimLaneToProject(@PathVariable String projectIdentifier,
                                                  @Valid @RequestBody SwimLaneEntity swimLaneEntity,
                                                  BindingResult bindingResult, Authentication authentication) {

        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            System.out.println("error map: " + errorMap);
            return errorMap;
        }
        User principal = (User) authentication.getPrincipal();
        Optional<ProjectEntity> project = projectService.tryToFindProject(projectIdentifier, principal.getEmail());
        if (project.isPresent()) {
            swimLaneService.addSwimLaneToProject(project.get(), swimLaneEntity, principal.getEmail());
            Tickets allTicketsForProject = projectTicketService.getProjectDashboard(project.get(), principal.getEmail());
            return new ResponseEntity<>(allTicketsForProject, HttpStatus.OK);
        }
        throw new ProjectNotFoundException("No project found with identifier: " + projectIdentifier);

    }

    @PostMapping("/{projectIdentifier}/{swimLaneId}")
    public ResponseEntity<?> addTicketToSwimLane(@PathVariable String projectIdentifier,
                                                 @PathVariable String swimLaneId,
                                                 @Valid @RequestBody ProjectTicket projectTicket,
                                                 BindingResult bindingResult,
                                                 Authentication authentication) {

        ResponseEntity<?> validationErrors = validateIncomingRequest(bindingResult);
        if (validationErrors != null) return validationErrors;

        User principal = (User) authentication.getPrincipal();
        Optional<ProjectEntity> project = projectService.tryToFindProject(projectIdentifier, principal.getEmail());
        if(project.isPresent()){
            LinkedHashMap<String, ProjectTicket> newTicket = projectTicketService.addProjectTicketToProject(project.get(),
                    swimLaneId, projectTicket, principal.getEmail());
            return new ResponseEntity<>(newTicket, HttpStatus.OK);
        }
        throw new ProjectNotFoundException("No project found with identifier: " + projectIdentifier);

    }

    @DeleteMapping("/{projectIdentifier}/{id}")
    public ResponseEntity<?> removeTicketFromProject(@PathVariable String projectIdentifier,
                                                     @PathVariable Long id,
                                                     @Valid @RequestBody ProjectTicket projectTicket,
                                                     BindingResult bindingResult,
                                                     Authentication authentication) {

        ResponseEntity<?> validationErrors = validateIncomingRequest(bindingResult);
        if (validationErrors != null) return validationErrors;
        User principal = (User) authentication.getPrincipal();

        projectTicketService.removeTicketFromProject(projectTicket, principal.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{projectIdentifier}/{swimLaneId}")
    public ResponseEntity<?> updateSwimLaneTicketOrder(@PathVariable String projectIdentifier,
                                                       @Valid @RequestBody SwimLane swimLane, BindingResult bindingResult, Authentication authentication) {

        ResponseEntity<?> validationErrors = validateIncomingRequest(bindingResult);
        if (validationErrors != null) return validationErrors;
        User principal = (User) authentication.getPrincipal();

        projectTicketService.updateTicketOrderForSwimLane(projectIdentifier, swimLane, principal.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{projectIdentifier}")
    public ResponseEntity<?> updateSwimLanes(@PathVariable String projectIdentifier, @Valid @RequestBody List<SwimLane> swimLanes,
                                             BindingResult bindingResult, Authentication authentication) {
        ResponseEntity<?> validationErrors = validateIncomingRequest(bindingResult);
        if (validationErrors != null) return validationErrors;

        User principal = (User) authentication.getPrincipal();
        projectTicketService.updateTicketSwimLane(projectIdentifier, swimLanes, principal.getEmail());

        projectTicketService.updateTicketPositionInNewSwimLane(swimLanes, principal.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private ResponseEntity<?> validateIncomingRequest(BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            return errorMap;
        }
        return null;
    }
}
