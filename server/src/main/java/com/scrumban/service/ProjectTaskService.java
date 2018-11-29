package com.scrumban.service;

import com.scrumban.model.Backlog;
import com.scrumban.model.ProjectTask;
import com.scrumban.model.Status;
import com.scrumban.repository.BacklogRepository;
import com.scrumban.repository.ProjectTaskRepository;
import org.springframework.stereotype.Service;

import static com.scrumban.model.Priority.LOW;

@Service
public class ProjectTaskService {

    private ProjectTaskRepository projectTaskRepository;
    private BacklogRepository backlogRepository;


    public ProjectTaskService(ProjectTaskRepository projectTaskRepository, BacklogRepository backlogRepository) {
        this.projectTaskRepository = projectTaskRepository;
        this.backlogRepository = backlogRepository;
    }

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

        Backlog backlog = backlogRepository.findBacklogByProjectIdentifier(projectIdentifier);
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

    }
}
