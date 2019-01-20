package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectEntity saveProject(ProjectEntity projectEntity) {
        System.out.println("in here");
        ProjectEntity foundProjectEntity = tryToFindProject(projectEntity);
        if(foundProjectEntity ==null) {

            List<SwimLaneEntity> swimLaneEntitySet = new LinkedList<>();
            projectEntity.setSwimLaneEntities(swimLaneEntitySet);
            return projectRepository.save(projectEntity);
        }
        throw new ProjectIdException("projectEntity ID: " + getProjectIdentifier(projectEntity) + " already exists!");
    }


    public ProjectEntity updateProject(ProjectEntity projectEntity) {
        return projectRepository.save(projectEntity);

    }

    public Iterable<ProjectEntity> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProject(String projectIdentifier){
        ProjectEntity projectEntity = tryToFindProject(projectIdentifier);
        if(projectEntity == null){
            throw new ProjectIdException("projectEntity ID: " +projectIdentifier.toUpperCase() + " does not exist!");
        }
        projectEntity.getSwimLaneEntities().forEach(swimLane -> System.out.println(swimLane.getName()));
        projectRepository.delete(projectEntity);
    }

    private String getProjectIdentifier(ProjectEntity projectEntity) {
        return projectEntity.getProjectIdentifier().toUpperCase();
    }

    public ProjectEntity tryToFindProject(ProjectEntity projectEntity) {
        return projectRepository.findProjectByProjectIdentifier(getProjectIdentifier(projectEntity));
    }
    public ProjectEntity tryToFindProject(String projectIdentifier){
        return projectRepository.findProjectByProjectIdentifier(projectIdentifier);
    }
}