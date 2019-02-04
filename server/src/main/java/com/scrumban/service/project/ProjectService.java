package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectRepository;
import com.scrumban.repository.ProjectTicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProjectService {

    private ProjectRepository projectRepository;
    private ProjectTicketRepository projectTicketRepository;

    public ProjectService(ProjectRepository projectRepository, ProjectTicketRepository projectTicketRepository) {
        this.projectRepository = projectRepository;
        this.projectTicketRepository = projectTicketRepository;
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
        throw new ProjectIdentifierException("project ID: " + projectEntity.getProjectIdentifier() + " already exists!");
    }

    public ProjectEntity updateProject(ProjectEntity projectEntity) {
        Optional<ProjectEntity> foundProjectEntity = tryToFindProject(projectEntity.getProjectIdentifier());
        if(foundProjectEntity.isPresent()){
            return projectRepository.save(projectEntity);
        }
        throw new ProjectIdentifierException("projectEntity ID: " + projectEntity.getProjectIdentifier() + " not found!");

    }

    public Iterable<ProjectEntity> findAllProjects(){
        return projectRepository.findAll();
    }

    public void deleteProject(String projectIdentifier){
        Optional<ProjectEntity> projectEntity = tryToFindProject(projectIdentifier);
        if(!projectEntity.isPresent()){

            throw new ProjectIdentifierException("projectEntity ID: " + projectIdentifier.toUpperCase() + " does not exist!");
        }

        projectEntity.get().getProjectTickets().forEach(projectTicket -> projectTicketRepository.deleteProjectTicket(projectTicket.getId()));
        projectRepository.delete(projectEntity.get());

    }


    public Optional<ProjectEntity> tryToFindProject(String projectIdentifier){
        return projectRepository.findProjectByProjectIdentifier(projectIdentifier);
    }
}