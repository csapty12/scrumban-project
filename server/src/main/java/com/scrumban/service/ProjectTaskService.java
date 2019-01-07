package com.scrumban.service;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.*;
import com.scrumban.model.enums.Status;
import com.scrumban.repository.BacklogRepository;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.scrumban.model.enums.Priority.LOW;

@Service
public class ProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;
    private BacklogRepository backlogRepository;
    private ProjectRepository projectRepository;


    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository, ProjectRepository projectRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        try {
            Backlog backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier.toUpperCase());
            String projectAbbreviation = getProjectAbreviation(projectIdentifier);
            System.out.println("project abbrev: " + projectAbbreviation);


            projectTask.setBacklog(backlog);
            int incrementValue = 1;
            Integer backlogSequence = backlog.getPTSequence() + incrementValue;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectAbbreviation + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            if (projectTask.getPriority().isEmpty()) {
                System.out.println("priorty set to low");
                projectTask.setPriority(LOW.valueOf());
            }
            if (projectTask.getStatus().isEmpty()) {
                System.out.println("status set to backlog");
                projectTask.setStatus(Status.BACKLOG.toString());
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project ID: " + projectIdentifier + " not found");
        }

    }


    public Tasks getProjectTasksFromBacklog(String projectIdentifier) {
        List<ProjectTask> allProjectTasks = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier.toUpperCase());
        Tasks tasks = new Tasks();
        tasks.setTasks(addAllTasks(allProjectTasks));
        List<Map<String, ProjectDashboardColumn>> columns =  addColumn(allProjectTasks);
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

    public ProjectTask getProjectTaskFromProjectSequence(String backlogId, String projectSequence) {
        Project project = projectRepository.findProjectByProjectIdentifier(backlogId);
        if (project == null) {
            throw new ProjectNotFoundException("Project ID: " + backlogId + " not found");
        }
        ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found");
        }

        if (!projectTask.getProjectIdentifier().equals(backlogId)) {
            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found in this backlog");
        }
        return projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
    }

    public ProjectTask updateProjectTask(String backlogId, String projectSequence, ProjectTask updatedProjectTask) {
        ProjectTask projectTask = getProjectTaskFromProjectSequence(backlogId, projectSequence);
        if (projectTask != null) {
            projectTaskRepository.save(updatedProjectTask);
            return updatedProjectTask;
        }
        return null;
    }

    public void deleteTicketFromBacklog(String backlogId, String projectSequence) {
        ProjectTask projectTask = getProjectTaskFromProjectSequence(backlogId, projectSequence);
        if (projectTask != null) {
            projectTaskRepository.delete(projectTask);
        }

    }

    private List<Map<String, ProjectDashboardColumn>> addColumn(List<ProjectTask> allProjectTasks) {
        List<String> allColumns = new ArrayList<>();
        System.out.println("all project tasks: " + allProjectTasks);
        allProjectTasks.forEach(column -> {
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
            projectDashboardColumnMap.put(columnId, createColumnAndInsertTasks(columnId, columnName, allProjectTasks));
            columnObject.add(projectDashboardColumnMap);
            columnNumber++;
        }

        return columnObject;
    }

    private ProjectDashboardColumn createColumnAndInsertTasks(String columnId, String columnName, List<ProjectTask> allProjectTasks) {
        ProjectDashboardColumn projectDashboardColumn = new ProjectDashboardColumn();
        projectDashboardColumn.setId(columnId);
        projectDashboardColumn.setTitle(columnName);
        projectDashboardColumn.setTaskIds(getTaskIds(allProjectTasks, columnName));
        return projectDashboardColumn;
    }

    private ArrayList<String> getTaskIds(List<ProjectTask> allProjectTasks, String columnName) {
        ArrayList<String> projectTaskIds = new ArrayList<>();

        allProjectTasks.forEach(projectTask -> {
            if (projectTask.getStatus().equals(Status.get(columnName).toString())) {
                projectTaskIds.add(projectTask.getProjectSequence());
           }
        });
        System.out.println("project task ids for " + Status.get(columnName)
                + ": " + projectTaskIds);
        return projectTaskIds;
    }


    private List<Map<String, ProjectTask>> addAllTasks(List<ProjectTask> allProjectTasks) {
        List<Map<String, ProjectTask>> projectTaskList = new ArrayList<>();
        Map<String, ProjectTask> projectTaskMap = new HashMap<>();
        allProjectTasks.forEach(projectTask -> projectTaskMap.put(projectTask.getProjectSequence(), projectTask));
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
