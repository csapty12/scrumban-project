package com.scrumban.service;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.Project;
import com.scrumban.repository.ProjectRepository;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveOrUpdate(Project project){
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch(Exception e){
            throw new ProjectIdException("project ID: " + project.getProjectIdentifier().toUpperCase() + " already exists!");
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
}
