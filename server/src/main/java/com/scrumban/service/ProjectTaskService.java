package com.scrumban.service;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.*;
import com.scrumban.repository.BacklogRepository;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.scrumban.model.Priority.LOW;

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
            String projectAbbreviation= getProjectAbreviation(projectIdentifier);
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
                projectTask.setStatus(Status.BACKLOG.valueOf());
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project ID: " + projectIdentifier + " not found");
        }

    }

    private String getProjectAbreviation(String projectIdentifier) {
        Pattern pattern = Pattern.compile("\\b[a-zA-Z]");
        Matcher matcher = pattern.matcher(projectIdentifier);
        String abbrev = "";
        while (matcher.find())
            abbrev +=matcher.group();
        return abbrev;
    }

    public Tasks getProjectTasksFromBacklog(String projectIdentifier) {
        List<ProjectTask> allProjectTasks = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier.toUpperCase());
        Tasks tasks = new Tasks();
        tasks.setTasks(addAllTasks(allProjectTasks));
        tasks.setColumns(addColumn(allProjectTasks));

//        if (allProjectTasks.isEmpty()) {
//            throw new ProjectNotFoundException("Project ID: " + projectIdentifier + " not found");
//        }
        return tasks;
    }

    private List<Map<String, ProjectDashboardColumn>> addColumn(List<ProjectTask> allProjectTasks) {
        List<String> allColumns = new ArrayList<>();
        allProjectTasks.forEach(column -> allColumns.add(column.getStatus()));
        List<String> uniqueColumns = allColumns.stream().distinct().collect(Collectors.toList());
        System.out.printf("unique columns: " + uniqueColumns);
//        Set<ProjectTask> columns = allProjectTasks.stream().distinct().collect(Collectors.toSet());
//        for(ProjectTask p: columns){
//            System.out.println("column: "  + p.get );
//        }
        return null;
    }

    private List<Map<String,ProjectTask>> addAllTasks(List<ProjectTask> allProjectTasks) {
        List<Map<String,ProjectTask>> projectTaskList = new ArrayList<>();
        Map<String, ProjectTask> projectTaskMap = new HashMap<>();
        allProjectTasks.forEach(projectTask -> projectTaskMap.put(projectTask.getProjectSequence(), projectTask));
        projectTaskList.add(projectTaskMap);
        return projectTaskList;

    }

    public ProjectTask getProjectTaskFromProjectSequence(String backlogId, String projectSequence) {
        Project project  = projectRepository.findProjectByProjectIdentifier(backlogId);
        if(project==null){
            throw new ProjectNotFoundException("Project ID: " + backlogId + " not found");
        }
        ProjectTask projectTask = projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
        if(projectTask==null){
            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found");
        }

        if(!projectTask.getProjectIdentifier().equals(backlogId)){
            throw new ProjectNotFoundException("Project task ID: " + projectSequence + " not found in this backlog");
        }
        return projectTaskRepository.findProjectTaskByProjectSequence(projectSequence);
    }

    public ProjectTask updateProjectTask(String backlogId, String projectSequence, ProjectTask updatedProjectTask){
        ProjectTask projectTask = getProjectTaskFromProjectSequence(backlogId,projectSequence);
        if(projectTask !=null) {
            projectTaskRepository.save(updatedProjectTask);
            return updatedProjectTask;
        }
        return  null;
    }

    public void deleteTicketFromBacklog(String backlogId, String projectSequence) {
        ProjectTask projectTask = getProjectTaskFromProjectSequence(backlogId,projectSequence);
        if(projectTask !=null){
            projectTaskRepository.delete(projectTask);
        }

    }
}
