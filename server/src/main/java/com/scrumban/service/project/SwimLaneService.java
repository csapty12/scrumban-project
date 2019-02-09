package com.scrumban.service.project;

import com.scrumban.exception.DuplicateProjectSwimLaneException;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SwimLaneService {

    private ProjectService projectService;
    private SwimLaneRepository swimLaneRepository;

    public SwimLaneService(ProjectService projectService, SwimLaneRepository swimLaneRepository) {
        this.projectService = projectService;
        this.swimLaneRepository = swimLaneRepository;
    }

    public ProjectEntity addSwimLaneToProject(String projectIdentifier, SwimLaneEntity swimLaneEntity) {

        Optional<ProjectEntity> projectEntity = projectService.tryToFindProject(projectIdentifier);

        SwimLaneEntity foundSwimLane = swimLaneRepository.findByName(swimLaneEntity.getName());
        if (foundSwimLane == null) {
            SwimLaneEntity newSwimLaneEntity = swimLaneRepository.save(swimLaneEntity);
            List<SwimLaneEntity> swimLaneEntities = projectEntity.get().getSwimLaneEntities();
            swimLaneEntities.add(newSwimLaneEntity);
        } else {
            List<SwimLaneEntity> projectSwimLanes = projectEntity.get().getSwimLaneEntities();
            if(projectSwimLanes.contains(foundSwimLane)){
                throw new DuplicateProjectSwimLaneException("Swim lane already exists in this project");
            }
            else{
                projectSwimLanes.add(foundSwimLane);
            }

        }
        return projectService.updateProject(projectEntity.get());
    }




    public SwimLaneEntity findSwimLaneByName(String swimLaneName) {
        return swimLaneRepository.findByName(swimLaneName);
    }

}
