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
        List<ProjectTicket> allProjectTickets= project.getProjectTickets();
        allProjectTickets.forEach(ticket-> System.out.println("ticket in getProjectDashboard: " + ticket.getProjectSequence()));
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
        int projectTicketSequence = project.getProjectTickets().size();
        int incrementValue = 1;
        int newProjectTicketSequenceValue =projectTicketSequence+incrementValue;
        projectTicket.setProjectSequence(projectIdentifier+ "-" + newProjectTicketSequenceValue);
        SwimLane swimLane = swimLaneService.findSwimLaneByName(swimLaneName);
        projectTicket.setProject(project);
        projectTicket.setSwimLane(swimLane);
        projectTicket.setProjectIdentifier(projectIdentifier);
        projectTicketRepository.save(projectTicket);

        return projectTicket;
    }

    private List<Map<String, ProjectDashboardColumn>> addSwimLaneWithTickets(Project project) {

        List<String> columnNames = new ArrayList<>();

        project.getSwimLanes().forEach(column -> {

            columnNames.add(column.getName());
        });
        System.out.println("column names: " + columnNames);
        List<ProjectTicket> allProjectTickets = project.getProjectTickets();

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

    private ProjectDashboardColumn createColumnAndInsertTasks(String columnId, String columnName, List<ProjectTicket> allProjectTickets) {
        return ProjectDashboardColumn
                .builder()
                .id(columnId)
                .title(columnName)
                .ticketIds(getTicketIds(allProjectTickets, columnName)).build();
    }

    private ArrayList<String> getTicketIds(List<ProjectTicket> allProjectTickets, String columnName) {
        for(ProjectTicket projectTicket: allProjectTickets){
            System.out.println("ticket: " + projectTicket.getSummary());
        }
        ArrayList<String> projectTaskIds = new ArrayList<>();

        allProjectTickets.forEach(projectTicket -> {
            if (projectTicket.getSwimLane().getName().equals(columnName)) {

                projectTaskIds.add(projectTicket.getProjectSequence());
            }
        });
        return projectTaskIds;
    }


    private List<LinkedHashMap<String, ProjectTicket>> insertAllTickets(List<ProjectTicket> allProjectTickets) {
        List<LinkedHashMap<String, ProjectTicket>> projectTicketList = new ArrayList<>();
        LinkedHashMap<String, ProjectTicket> projectTicketMap = new LinkedHashMap<>();
        allProjectTickets.forEach(ticket -> projectTicketMap.put(ticket.getProjectSequence(), ticket));
        projectTicketList.add(projectTicketMap);

        System.out.println("project ticket list: " + projectTicketList);
        return projectTicketList;
    }



    public void removeTicketFromProject(ProjectTicket projectTicket1) {
        System.out.println("ticket id " + projectTicket1.getId());
        projectTicketRepository.deleteProjectTicket(projectTicket1.getId());
    }
}
