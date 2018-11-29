package com.scrumban.service;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.Backlog;
import com.scrumban.model.Project;
import com.scrumban.repository.BacklogRepository;
import com.scrumban.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private BacklogRepository backlogRepository;

    public ProjectService(ProjectRepository projectRepository, BacklogRepository backlogRepository) {
        this.projectRepository = projectRepository;
        this.backlogRepository = backlogRepository;
    }

    public Project saveOrUpdate(Project project){
        try{
            project.setProjectIdentifier(getProjectIdentifier(project));
            if(project.getId()==null){
                Backlog backlog = new Backlog(); //if you are saving a new project, create a new backlog, set the backlog fo the project and set the project to the backlog.
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(getProjectIdentifier(project));
            }
            else{
                project.setBacklog(getProjectBacklog(project));
            }

            return projectRepository.save(project);
        }catch(Exception e){
            throw new ProjectIdException("project ID: " + getProjectIdentifier(project) + " already exists!");
        }

    }

    public Project getProject(String projectIdentifier){
        return projectRepository.findProjectByProjectIdentifier(projectIdentifier);
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProject(String projectIdentifier){
        Project project = getProject(projectIdentifier);
        if(project == null){
            throw new ProjectIdException("project ID: " +projectIdentifier.toUpperCase() + " does not exist!");
        }
        projectRepository.delete(project);
    }

    private String getProjectIdentifier(Project project) {
        return project.getProjectIdentifier().toUpperCase();
    }

    private Backlog getProjectBacklog(Project project) {
        return backlogRepository.findBacklogByProjectIdentifier(getProjectIdentifier(project));
    }
}
