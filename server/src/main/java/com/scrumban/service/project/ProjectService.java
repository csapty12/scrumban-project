package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.project.Project;
import com.scrumban.model.project.ProjectTickets;
import com.scrumban.model.project.SwimLane;
import com.scrumban.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project saveProject(Project project){
        System.out.println("in here");
        Project foundProject = tryToFindProject(project);
        if(foundProject==null) {
            Set<ProjectTickets> projectTicketsSet = new HashSet<>();
            Set<SwimLane> swimLaneSet = new HashSet<>();
            project.setProjectTickets(projectTicketsSet);
            project.setSwimLanes(swimLaneSet);
            return projectRepository.save(project);
        }
        throw new ProjectIdException("project ID: " + getProjectIdentifier(project) + " already exists!");
    }
    public Project updateProject(Project project) {
        System.out.println("now in here");
        return projectRepository.save(project);

    }


    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProject(String projectIdentifier){
        Project project = tryToFindProject(projectIdentifier);
        if(project == null){
            throw new ProjectIdException("project ID: " +projectIdentifier.toUpperCase() + " does not exist!");
        }
        projectRepository.delete(project);
    }

    private String getProjectIdentifier(Project project) {
        return project.getProjectIdentifier().toUpperCase();
    }

    private Project tryToFindProject(Project project) {
        return projectRepository.findProjectByProjectIdentifier(getProjectIdentifier(project));
    }
    private Project tryToFindProject(String projectIdentifier){
        return projectRepository.findProjectByProjectIdentifier(projectIdentifier);
    }


}