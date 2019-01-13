//package com.scrumban.service;
//
//import com.scrumban.exception.ProjectNotFoundException;
//import com.scrumban.model.*;
//import com.scrumban.model.enums.Status;
//import com.scrumban.model.project.Project;
//import com.scrumban.model.project.ProjectTicket;
//import com.scrumban.repository.ProjectRepository;
//import com.scrumban.repository.ProjectTaskRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//
//@Service
//public class ProjectTaskService {
//
//    private ProjectTaskRepository projectTaskRepository;
//    private ProjectRepository projectRepository;
//
//
//    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository) {
//        this.projectTaskRepository = projectTaskRepository;
//        this.projectRepository = projectRepository;
//    }
//
//
//
//
//    public Tickets getProjectTasksFromBacklog(String projectIdentifier) {
//        List<ProjectTicket> allProjectTickets = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier.toUpperCase());
//        Tickets tickets = new Tickets();
//        tickets.setTickets(addAllTasks(allProjectTickets));
//        List<Map<String, ProjectDashboardColumn>> columns =  addColumn(allProjectTickets);
//        List<String> columnOrder = new ArrayList<>();
//        columns.forEach(column->{
//            for ( String key : column.keySet() ) {
//                columnOrder.add(key);
//            }
//        });
//        tickets.setColumns(columns);
//        tickets.setColumnOrder(columnOrder);
//        return tickets;
//    }
//
//    public ProjectTicket getProjectTaskFromProjectSequence(String backlogId, String projectSequence) {
//        Project project = projectRepository.findProjectByProjectIdentifier(backlogId);
//        if (project == null) {
//            throw new ProjectNotFoundException("Project ID: " + backlogId + " not found");
//        }
//        ProjectTicket projectTicket = projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
//        if (projectTicket == null) {
//            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found");
//        }
//
//        if (!projectTicket.getProjectIdentifier().equals(backlogId)) {
//            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found in this backlog");
//        }
//        return projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
//    }
//
//    public ProjectTicket updateProjectTask(String backlogId, String projectSequence, ProjectTicket updatedProjectTicket) {
//        ProjectTicket projectTicket = getProjectTaskFromProjectSequence(backlogId, projectSequence);
//        if (projectTicket != null) {
//            projectTaskRepository.save(updatedProjectTicket);
//            return updatedProjectTicket;
//        }
//        return null;
//    }
//
//    public void deleteTicketFromBacklog(String backlogId, String projectSequence) {
//        ProjectTicket projectTicket = getProjectTaskFromProjectSequence(backlogId, projectSequence);
//        if (projectTicket != null) {
//            projectTaskRepository.delete(projectTicket);
//        }
//
//    }
//
//    private List<Map<String, ProjectDashboardColumn>> addColumn(List<ProjectTicket> allProjectTickets) {
//        List<String> allColumns = new ArrayList<>();
//        System.out.println("all project tasks: " + allProjectTickets);
//        allProjectTickets.forEach(column -> {
//            System.out.println("column: " + column.getProjectSequence() + " >> " +  column.getStatus());
//            allColumns.add(Status.valueOf(column.getStatus()).getStatusValue());
//        });
//
//        System.out.println("All columns: " + allColumns );
//        List<String> uniqueColumns = allColumns.stream().distinct().collect(Collectors.toList());
//        System.out.println("unique columns: " + uniqueColumns);
//
//
//        List<Map<String, ProjectDashboardColumn>> columnObject = new ArrayList<>();
//        int columnNumber = 1;
//        for (String columnName : uniqueColumns) {
//            Map<String, ProjectDashboardColumn> projectDashboardColumnMap = new HashMap<>();
//            String columnId = "column-" + columnNumber;
//            projectDashboardColumnMap.put(columnId, createColumnAndInsertTasks(columnId, columnName, allProjectTickets));
//            columnObject.add(projectDashboardColumnMap);
//            columnNumber++;
//        }
//
//        return columnObject;
//    }
//
//    private ProjectDashboardColumn createColumnAndInsertTasks(String columnId, String columnName, List<ProjectTicket> allProjectTickets) {
//        ProjectDashboardColumn projectDashboardColumn = new ProjectDashboardColumn();
//        projectDashboardColumn.setId(columnId);
//        projectDashboardColumn.setTitle(columnName);
//        projectDashboardColumn.setTaskIds(getTaskIds(allProjectTickets, columnName));
//        return projectDashboardColumn;
//    }
//
//    private ArrayList<String> getTaskIds(List<ProjectTicket> allProjectTickets, String columnName) {
//        ArrayList<String> projectTaskIds = new ArrayList<>();
//
//        allProjectTickets.forEach(projectTask -> {
//            if (projectTask.getStatus().equals(Status.get(columnName).toString())) {
//                projectTaskIds.add(projectTask.getProjectSequence());
//            }
//        });
//        System.out.println("project task ids for " + Status.get(columnName)
//                + ": " + projectTaskIds);
//        return projectTaskIds;
//    }
//
//
//    private List<Map<String, ProjectTicket>> addAllTasks(List<ProjectTicket> allProjectTickets) {
//        List<Map<String, ProjectTicket>> projectTaskList = new ArrayList<>();
//        Map<String, ProjectTicket> projectTaskMap = new HashMap<>();
//        allProjectTickets.forEach(projectTask -> projectTaskMap.put(projectTask.getProjectSequence(), projectTask));
//        projectTaskList.add(projectTaskMap);
//        return projectTaskList;
//
//    }
//
//    private String getProjectAbreviation(String projectIdentifier) {
//        Pattern pattern = Pattern.compile("\\b[a-zA-Z]");
//        Matcher matcher = pattern.matcher(projectIdentifier);
//        String abbrev = "";
//        while (matcher.find())
//            abbrev += matcher.group();
//        return abbrev;
//    }
//}