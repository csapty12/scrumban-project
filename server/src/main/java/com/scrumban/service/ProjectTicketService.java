package com.scrumban.service;

import com.scrumban.model.ProjectDashboardColumn;
import com.scrumban.model.Tickets;
import com.scrumban.model.project.Project;
import com.scrumban.model.project.ProjectTicket;
import com.scrumban.model.project.SwimLane;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public Tickets getProjectDashboard(String projectIdentifier) {
        Project project = projectService.tryToFindProject(projectIdentifier);
        Set<ProjectTicket> allProjectTickets= project.getProjectTickets();
        Tickets tickets = new Tickets();
        tickets.setTickets(insertAllTickets(allProjectTickets));

        List<Map<String, ProjectDashboardColumn>> swimLanesAndTicketReferences = addSwimLaneWithTickets(project);
        tickets.setSwimLanes(swimLanesAndTicketReferences);

        List<String> swimLaneOrder = new ArrayList<>();
        swimLanesAndTicketReferences.forEach(column->{
            for ( String key : column.keySet() ) {
                swimLaneOrder.add(key);
            }
        });
        tickets.setSwimLaneOrder(swimLaneOrder);

        return tickets;

    }

    public ProjectTicket addProjectTicketToProject(String projectIdentifier, String swimLaneName, ProjectTicket projectTicket) {

        Project project = projectService.tryToFindProject(projectIdentifier);
        SwimLane swimLane = swimLaneService.findSwimLaneByName(swimLaneName);
        projectTicket.setProject(project);
        projectTicket.setSwimLane(swimLane);
        projectTicket.setProjectIdentifier(projectIdentifier);
        projectTicketRepository.save(projectTicket);
        return projectTicket;
    }

    private List<Map<String, ProjectDashboardColumn>> addSwimLaneWithTickets(Project project) {

        Set<String> columnNames = new LinkedHashSet<>();

        project.getSwimLanes().forEach(column -> {

            columnNames.add(column.getName());
        });  //this needs to be changed to get the set in order.
        System.out.println("column names: " + columnNames);
        Set<ProjectTicket> allProjectTickets = project.getProjectTickets();

        int columnNumber = 1;
        List<Map<String, ProjectDashboardColumn>> listOfColumns = new ArrayList<>();
        for (String columnName : columnNames) {
            Map<String, ProjectDashboardColumn> projectDashboardColumn = new HashMap<>();
            String columnId = "column-" + columnNumber;
            projectDashboardColumn.put(columnId, createColumnAndInsertTasks(columnId, columnName, allProjectTickets));
            listOfColumns.add(projectDashboardColumn);
            columnNumber++;
        }
        System.out.println("list of columns: " + listOfColumns);
        return listOfColumns;

    }

    private ProjectDashboardColumn createColumnAndInsertTasks(String columnId, String columnName, Set<ProjectTicket> allProjectTickets) {
        return ProjectDashboardColumn
                .builder()
                .id(columnId)
                .title(columnName)
                .ticketIds(getTicketIds(allProjectTickets, columnName)).build();
    }

    private ArrayList<String> getTicketIds(Set<ProjectTicket> allProjectTickets, String columnName) {
        ArrayList<String> projectTaskIds = new ArrayList<>();

        allProjectTickets.forEach(projectTicket -> {
            if (projectTicket.getSwimLane().getName().equals(columnName)) {

                projectTaskIds.add(projectTicket.getProjectSequence());
            }
        });
        return projectTaskIds;
    }


    private List<LinkedHashMap<String, ProjectTicket>> insertAllTickets(Set<ProjectTicket> allProjectTickets) {
        List<LinkedHashMap<String, ProjectTicket>> projectTicketList = new ArrayList<>();
        LinkedHashMap<String, ProjectTicket> projectTicketMap = new LinkedHashMap<>();
        allProjectTickets.forEach(ticket -> projectTicketMap.put(ticket.getProjectSequence(), ticket));
        projectTicketList.add(projectTicketMap);

        System.out.println("project ticket list: " + projectTicketList);
        return projectTicketList;
    }

}
