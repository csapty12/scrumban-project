package com.scrumban.service;

import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.Backlog;
import com.scrumban.model.Project;
import com.scrumban.model.ProjectTask;
import com.scrumban.model.Status;
import com.scrumban.repository.BacklogRepository;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

            projectTask.setBacklog(backlog);
            int incrementValue = 1;
            Integer backlogSequence = backlog.getPTSequence() + incrementValue;
            backlog.setPTSequence(backlogSequence);
            projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);
            if (projectTask.getPriority() == null) {
                projectTask.setPriority(LOW);
            }
            if (projectTask.getStatus() == null) {
                projectTask.setStatus(Status.TO_DO.getStatus());
            }
            return projectTaskRepository.save(projectTask);
        } catch (Exception e) {
            throw new ProjectNotFoundException("Project ID: " + projectIdentifier + " not found");
        }

    }

    public List<ProjectTask> getProjectTasksFromBacklog(String projectIdentifier) {
        List<ProjectTask> allProjectTasks = projectTaskRepository.findAllByProjectIdentifier(projectIdentifier.toUpperCase());
        if (allProjectTasks.isEmpty()) {
            throw new ProjectNotFoundException("Project ID: " + projectIdentifier + " not found");
        }
        return allProjectTasks;
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
}
