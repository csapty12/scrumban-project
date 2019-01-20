package com.scrumban.service;

import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.service.project.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SwimLaneService {

    private ProjectService projectService;
    private SwimLaneRepository swimLaneRepository;

    public SwimLaneService(ProjectService projectService, SwimLaneRepository swimLaneRepository) {
        this.projectService = projectService;
        this.swimLaneRepository = swimLaneRepository;
    }

    public ProjectEntity addSwimLaneToProject(String projectIdentifier, SwimLaneEntity swimLaneEntity) {
        System.out.println("adding swimlane to table");

        ProjectEntity projectEntity = projectService.tryToFindProject(projectIdentifier);

        SwimLaneEntity foundSwimLand = swimLaneRepository.findByName(swimLaneEntity.getName());
        if (foundSwimLand == null) {
            System.out.println("saving new swimlane");
            SwimLaneEntity newSwimLaneEntity = swimLaneRepository.save(swimLaneEntity);
            List<SwimLaneEntity> swimLaneEntities = projectEntity.getSwimLaneEntities();
            swimLaneEntities.add(newSwimLaneEntity);
        } else {
            System.out.println("swimlane already found");
            List<SwimLaneEntity> swimLaneEntities = projectEntity.getSwimLaneEntities();
            swimLaneEntities.add(foundSwimLand);

        }
        return projectService.updateProject(projectEntity);
    }





    public SwimLaneEntity findSwimLaneByName(String swimLaneName) {
        return swimLaneRepository.findByName(swimLaneName);
    }

}
