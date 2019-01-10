package com.scrumban.service;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.*;
import com.scrumban.model.enums.Status;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;
    private ProjectRepository projectRepository;


    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, ProjectRepository projectRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.projectRepository = projectRepository;
    }

//    public ProjectTickets addProjectTask(String projectIdentifier, ProjectTickets projectTickets) {
//        try {
////            Backlog backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier.toUpperCase());
//            String projectAbbreviation = getProjectAbreviation(projectIdentifier);
//            System.out.println("project abbrev: " + projectAbbreviation);
//
//
//            projectTickets.setBacklog(backlog);
//            int incrementValue = 1;
//            Integer backlogSequence = backlog.getPTSequence() + incrementValue;
//            backlog.setPTSequence(backlogSequence);
//            projectTickets.setProjectSequence(projectAbbreviation + "-" + backlogSequence);
//            projectTickets.setProjectIdentifier(projectIdentifier);
//            if (projectTickets.getPriority().isEmpty()) {
//                System.out.println("priorty set to low");
//                projectTickets.setPriority(LOW.valueOf());
//            }
//            if (projectTickets.getStatus().isEmpty()) {
//                System.out.println("status set to backlog");
//                projectTickets.setStatus(Status.BACKLOG.toString());
//            }
//            return projectTaskRepository.save(projectTickets);
//        } catch (Exception e) {
//            throw new ProjectNotFoundException("Project ID: " + projectIdentifier + " not found");
//        }
//
//    }


    public Tasks getProjectTasksFromBacklog(String projectIdentifier) {
        List<ProjectTickets> allProjectTickets = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier.toUpperCase());
        Tasks tasks = new Tasks();
        tasks.setTasks(addAllTasks(allProjectTickets));
        List<Map<String, ProjectDashboardColumn>> columns =  addColumn(allProjectTickets);
        List<String> columnOrder = new ArrayList<>();
        columns.forEach(column->{
            for ( String key : column.keySet() ) {
                columnOrder.add(key);
            }
        });
        tasks.setColumns(columns);
        tasks.setColumnOrder(columnOrder);
        return tasks;
    }

    public ProjectTickets getProjectTaskFromProjectSequence(String backlogId, String projectSequence) {
        Project project = projectRepository.findProjectByProjectIdentifier(backlogId);
        if (project == null) {
            throw new ProjectNotFoundException("Project ID: " + backlogId + " not found");
        }
        ProjectTickets projectTickets = projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
        if (projectTickets == null) {
            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found");
        }

        if (!projectTickets.getProjectIdentifier().equals(backlogId)) {
            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found in this backlog");
        }
        return projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
    }

    public ProjectTickets updateProjectTask(String backlogId, String projectSequence, ProjectTickets updatedProjectTickets) {
        ProjectTickets projectTickets = getProjectTaskFromProjectSequence(backlogId, projectSequence);
        if (projectTickets != null) {
            projectTaskRepository.save(updatedProjectTickets);
            return updatedProjectTickets;
        }
        return null;
    }

    public void deleteTicketFromBacklog(String backlogId, String projectSequence) {
        ProjectTickets projectTickets = getProjectTaskFromProjectSequence(backlogId, projectSequence);
        if (projectTickets != null) {
            projectTaskRepository.delete(projectTickets);
        }

    }

    private List<Map<String, ProjectDashboardColumn>> addColumn(List<ProjectTickets> allProjectTickets) {
        List<String> allColumns = new ArrayList<>();
        System.out.println("all project tasks: " + allProjectTickets);
        allProjectTickets.forEach(column -> {
            System.out.println("column: " + column.getProjectSequence() + " >> " +  column.getStatus());
            allColumns.add(Status.valueOf(column.getStatus()).getStatusValue());
        });

        System.out.println("All columns: " + allColumns );
        List<String> uniqueColumns = allColumns.stream().distinct().collect(Collectors.toList());
        System.out.println("unique columns: " + uniqueColumns);


        List<Map<String, ProjectDashboardColumn>> columnObject = new ArrayList<>();
        int columnNumber = 1;
        for (String columnName : uniqueColumns) {
            Map<String, ProjectDashboardColumn> projectDashboardColumnMap = new HashMap<>();
            String columnId = "column-" + columnNumber;
            projectDashboardColumnMap.put(columnId, createColumnAndInsertTasks(columnId, columnName, allProjectTickets));
            columnObject.add(projectDashboardColumnMap);
            columnNumber++;
        }

        return columnObject;
    }

    private ProjectDashboardColumn createColumnAndInsertTasks(String columnId, String columnName, List<ProjectTickets> allProjectTickets) {
        ProjectDashboardColumn projectDashboardColumn = new ProjectDashboardColumn();
        projectDashboardColumn.setId(columnId);
        projectDashboardColumn.setTitle(columnName);
        projectDashboardColumn.setTaskIds(getTaskIds(allProjectTickets, columnName));
        return projectDashboardColumn;
    }

    private ArrayList<String> getTaskIds(List<ProjectTickets> allProjectTickets, String columnName) {
        ArrayList<String> projectTaskIds = new ArrayList<>();

        allProjectTickets.forEach(projectTask -> {
            if (projectTask.getStatus().equals(Status.get(columnName).toString())) {
                projectTaskIds.add(projectTask.getProjectSequence());
            }
        });
        System.out.println("project task ids for " + Status.get(columnName)
                + ": " + projectTaskIds);
        return projectTaskIds;
    }


    private List<Map<String, ProjectTickets>> addAllTasks(List<ProjectTickets> allProjectTickets) {
        List<Map<String, ProjectTickets>> projectTaskList = new ArrayList<>();
        Map<String, ProjectTickets> projectTaskMap = new HashMap<>();
        allProjectTickets.forEach(projectTask -> projectTaskMap.put(projectTask.getProjectSequence(), projectTask));
        projectTaskList.add(projectTaskMap);
        return projectTaskList;

    }

    private String getProjectAbreviation(String projectIdentifier) {
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]");
        Matcher matcher = pattern.matcher(projectIdentifier);
        String abbrev = "";
        while (matcher.find())
            abbrev += matcher.group();
        return abbrev;
    }
}