package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectService {

    private ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectEntity saveProject(ProjectEntity projectEntity) {
        Optional<ProjectEntity> foundProjectEntity = tryToFindProject(projectEntity.getProjectIdentifier().toUpperCase());
        log.info("attempting to save project: {}", projectEntity.getProjectName());
        if(!foundProjectEntity.isPresent()) {
            List<SwimLaneEntity> swimLaneEntitySet = new LinkedList<>();
            projectEntity.setSwimLaneEntities(swimLaneEntitySet);
            ProjectEntity savedNewProject = projectRepository.save(projectEntity);
            log.info("new project: {} has been saved", savedNewProject.getProjectName());
            return savedNewProject;
        }
        throw new ProjectIdException("project ID: " + projectEntity.getProjectIdentifier() + " already exists!");
    }

    public ProjectEntity updateProject(ProjectEntity projectEntity) {
        Optional<ProjectEntity> foundProjectEntity = tryToFindProject(projectEntity.getProjectIdentifier());
        if(foundProjectEntity.isPresent()){
            return projectRepository.save(projectEntity);
        }
        throw new ProjectIdException("projectEntity ID: " + projectEntity.getProjectIdentifier() + " not found!");

    }

    public Iterable<ProjectEntity> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProject(String projectIdentifier){
        Optional<ProjectEntity> projectEntity = tryToFindProject(projectIdentifier);
        if(!projectEntity.isPresent()){

            throw new ProjectIdException("projectEntity ID: " + projectIdentifier.toUpperCase() + " does not exist!");
        }
        projectRepository.delete(projectEntity.get());

    }


    public Optional<ProjectEntity> tryToFindProject(String projectIdentifier){
        return projectRepository.findProjectByProjectIdentifier(projectIdentifier);
    }
}