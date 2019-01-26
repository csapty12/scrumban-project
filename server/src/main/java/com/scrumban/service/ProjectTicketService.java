package com.scrumban.service;

import com.scrumban.model.ProjectDashboardColumn;
import com.scrumban.model.Tickets;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Optional<ProjectEntity> projectEntity = projectService.tryToFindProject(projectIdentifier);
        List<ProjectTicket> allProjectTickets= projectEntity.get().getProjectTickets();
        allProjectTickets.forEach(ticket-> System.out.println("ticket in getProjectDashboard: " + ticket.getProjectSequence()));
        Tickets tickets = new Tickets();
        List<LinkedHashMap<String, ProjectTicket>> allTickets = insertAllTickets(allProjectTickets);
        tickets.setTickets(allTickets);

        List<Map<String, ProjectDashboardColumn>> swimLanesAndTicketReferences = addSwimLaneWithTickets(projectEntity);
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

    public LinkedHashMap<String, ProjectTicket> addProjectTicketToProject(String projectIdentifier, String swimLaneName, ProjectTicket projectTicket) {

        Optional<ProjectEntity> projectEntity = projectService.tryToFindProject(projectIdentifier);
        int currentTicketNumber = projectEntity.get().getCurrentTicketNumber();
        System.out.println("current ticket number= " + currentTicketNumber);
        int incrementValue = 1;
        String acronym = getAcronymFromProjectIdentifier(projectIdentifier);
        int newProjectTicketSequenceValue =currentTicketNumber+incrementValue;
        String projectSequence = acronym + "-" + newProjectTicketSequenceValue;
        projectTicket.setProjectSequence(projectSequence);
        SwimLaneEntity swimLaneEntity = swimLaneService.findSwimLaneByName(swimLaneName);
        projectTicket.setProject(projectEntity.get());
        projectTicket.setSwimLaneEntity(swimLaneEntity);
        projectTicket.setProjectIdentifier(projectIdentifier);
        projectTicketRepository.save(projectTicket);

        LinkedHashMap<String, ProjectTicket> singleProjectTicket= new LinkedHashMap<>();
        singleProjectTicket.put(projectSequence, projectTicket );

        return singleProjectTicket;
    }

    private String getAcronymFromProjectIdentifier(String projectIdentifier) {
        String initials = Arrays.stream(projectIdentifier.split("-"))
                .map(s -> s.substring(0, 1))
                .collect(Collectors.joining());
        System.out.println(initials);
        return initials.toUpperCase();
    }

    private List<Map<String, ProjectDashboardColumn>> addSwimLaneWithTickets(Optional<ProjectEntity> projectEntity) {

        List<String> columnNames = new ArrayList<>();

        projectEntity.get().getSwimLaneEntities().forEach(column -> {

            columnNames.add(column.getName());
        });
        System.out.println("column names: " + columnNames);

        List<ProjectTicket> allProjectTickets = projectEntity.get().getProjectTickets();


        List<Map<String, ProjectDashboardColumn>> listOfColumns = new ArrayList<>();
        for (String columnName : columnNames) {
            Map<String, ProjectDashboardColumn> projectDashboardColumn = new HashMap<>();
            projectDashboardColumn.put(columnName, createColumnAndInsertTasks(columnName, allProjectTickets));
            listOfColumns.add(projectDashboardColumn);
        }
        System.out.println("list of columns: " + listOfColumns);
        return listOfColumns;

    }

    private ProjectDashboardColumn createColumnAndInsertTasks(String columnName, List<ProjectTicket> allProjectTickets) {
        return ProjectDashboardColumn
                .builder()
                .title(columnName)
                .ticketIds(getTicketIds(allProjectTickets, columnName)).build();
    }

    private ArrayList<String> getTicketIds(List<ProjectTicket> allProjectTickets, String columnName) {
        ArrayList<String> projectTaskIds = new ArrayList<>();

        allProjectTickets.forEach(projectTicket -> {
            if (projectTicket.getSwimLaneEntity().getName().equals(columnName)) {

                projectTaskIds.add(projectTicket.getProjectSequence());
            }
        });
        return projectTaskIds;
    }


    private List<LinkedHashMap<String, ProjectTicket>> insertAllTickets(List<ProjectTicket> allProjectTickets) {
        if(allProjectTickets.size()==0){
            return new ArrayList<>();
        }
        List<LinkedHashMap<String, ProjectTicket>> projectTicketList = new ArrayList<>();
        LinkedHashMap<String, ProjectTicket> projectTicketMap = new LinkedHashMap<>();

        allProjectTickets.forEach(ticket -> projectTicketMap.put(ticket.getProjectSequence(), ticket));
        projectTicketList.add(projectTicketMap);

        System.out.println("projectEntity ticket list: " + projectTicketList);
        System.out.println("projectTicketList size: " + allProjectTickets.size());
        return projectTicketList;
    }



    public void removeTicketFromProject(ProjectTicket projectTicket1) {
        System.out.println("ticket id " + projectTicket1.getId());
        projectTicketRepository.deleteProjectTicket(projectTicket1.getId());
    }
}
