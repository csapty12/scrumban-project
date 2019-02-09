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

    public ProjectEntity addSwimLaneToProject(ProjectEntity project, SwimLaneEntity swimLaneEntity) {

        Optional<SwimLaneEntity> foundSwimLane = swimLaneRepository.findByName(swimLaneEntity.getName());
        List<SwimLaneEntity> projectSwimLanes = project.getSwimLaneEntities();
        if (!foundSwimLane.isPresent()) {
            SwimLaneEntity newSwimLaneEntity = swimLaneRepository.save(swimLaneEntity);
            projectSwimLanes.add(newSwimLaneEntity);
        } else {
            if (projectSwimLanes.contains(foundSwimLane.get())) {
                throw new DuplicateProjectSwimLaneException("Swim lane already exists in this project");
            }
            projectSwimLanes.add(foundSwimLane.get());

        }
        return projectService.updateProject(project);
    }


    public Optional<SwimLaneEntity> findSwimLaneByName(String swimLaneName) {
        return swimLaneRepository.findByName(swimLaneName);
    }

}
