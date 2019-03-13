package com.scrumban.service.project;

import com.scrumban.exception.DuplicateProjectSwimLaneException;
import com.scrumban.exception.ProjectNotFoundException;
import com.scrumban.model.domain.SwimLane;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.service.user.UserService;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SwimLaneService {

    private ProjectService projectService;
    private SwimLaneRepository swimLaneRepository;
    private UserService userService;

    public SwimLaneService(ProjectService projectService, SwimLaneRepository swimLaneRepository, UserService userService) {
        this.projectService = projectService;
        this.swimLaneRepository = swimLaneRepository;
        this.userService = userService;
    }

    public Map<String, SwimLane> addSwimLaneToProject(String projectIdentifier, SwimLaneEntity swimLaneEntity, String userEmail) {
        User user = userService.getUser(userEmail);
        Optional<ProjectEntity> project = projectService.getProject(projectIdentifier, user);

        if (project.isPresent()) {
            Optional<SwimLaneEntity> foundSwimLane = swimLaneRepository.findByName(swimLaneEntity.getName());
            List<SwimLaneEntity> projectSwimLanes = project.get().getSwimLaneEntities();
            insertSwimLaneToProject(swimLaneEntity, foundSwimLane, projectSwimLanes);
            projectService.updateProject(project.get(), userEmail);
            return  createNewSwimLaneObject(swimLaneEntity);
        }
        throw new ProjectNotFoundException("Project with ID: " + projectIdentifier + "not found");
    }

    public Optional<SwimLaneEntity> findSwimLaneByName(String swimLaneName) {
        return swimLaneRepository.findByName(swimLaneName);
    }

    private void insertSwimLaneToProject(SwimLaneEntity swimLaneEntity, Optional<SwimLaneEntity> foundSwimLane, List<SwimLaneEntity> projectSwimLanes) {
        if (!foundSwimLane.isPresent()) {
            SwimLaneEntity newSwimLaneEntity = swimLaneRepository.save(swimLaneEntity);
            projectSwimLanes.add(newSwimLaneEntity);
        } else {
            if (projectSwimLanes.contains(foundSwimLane.get())) {
                throw new DuplicateProjectSwimLaneException("Swim lane already exists in this project");
            }
            projectSwimLanes.add(foundSwimLane.get());
        }
    }
    private Map<String, SwimLane> createNewSwimLaneObject(SwimLaneEntity swimLaneEntity) {
        SwimLane newSwimLane = SwimLane.builder().title(swimLaneEntity.getName()).ticketIds(new ArrayList<>()).build();
        Map<String, SwimLane> swimLaneMap = new HashMap<>();
        swimLaneMap.put(swimLaneEntity.getName(), newSwimLane);
        return swimLaneMap;
    }
}
