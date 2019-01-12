package com.scrumban.service;

import com.scrumban.model.project.Project;
import com.scrumban.model.project.ProjectTicket;
import com.scrumban.model.project.SwimLane;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectTicketService {

    private ProjectTicketRepository projectTicketRepository;
    private ProjectService projectService;
    private SwimLaneService swimLaneService;

    public ProjectTicketService(ProjectTicketRepository projectTicketRepository, ProjectService projectService, SwimLaneService swimLaneService) {
        this.projectTicketRepository = projectTicketRepository;
        this.projectService = projectService;
        this.swimLaneService = swimLaneService;
    }

    public Set<ProjectTicket> getAllTicketsForProject(String projectIdentifier) {
        Set<ProjectTicket> findAllTickets = projectTicketRepository.findProjectTicketsByProjectIdentifier(projectIdentifier);
        findAllTickets.forEach(projectTicket -> System.out.println(projectTicket.getSwimLane().getName()));
        return findAllTickets;

    }

    public ProjectTicket addProjectTicketToProject(String projectIdentifier,  String swimLaneName, ProjectTicket projectTicket){

        Project project = projectService.tryToFindProject(projectIdentifier);
        SwimLane swimLane = swimLaneService.findSwimLaneByName(swimLaneName);

        System.out.println("project: " + project.getProjectIdentifier());
        projectTicket.setProject(project);
        projectTicket.setSwimLane(swimLane);
        projectTicket.setProjectIdentifier(projectIdentifier);
        projectTicketRepository.save(projectTicket);

        return projectTicket;
    }
}
