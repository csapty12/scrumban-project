package com.scrumban.service.project;

import com.scrumban.exception.DuplicateProjectSwimLaneException;
import com.scrumban.model.domain.SwimLane;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.ProjectTicketEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import com.scrumban.repository.entity.ProjectTicketEntityRepository;
import com.scrumban.repository.entity.SwimLaneEntityRepository;
import com.scrumban.validator.UserProjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SwimLaneService {

    private ProjectService projectService;
    private SwimLaneEntityRepository swimLaneEntityRepository;
    private UserProjectValidator userProjectValidator;
    private ProjectTicketEntityRepository projectTicketEntityRepository;

    public SwimLaneService(ProjectService projectService, SwimLaneEntityRepository swimLaneEntityRepository, UserProjectValidator userProjectValidator, ProjectTicketEntityRepository projectTicketEntityRepository) {
        this.projectService = projectService;
        this.swimLaneEntityRepository = swimLaneEntityRepository;
        this.userProjectValidator = userProjectValidator;
        this.projectTicketEntityRepository = projectTicketEntityRepository;
    }

    public Map<String, SwimLane> addSwimLaneToProject(String projectIdentifier, SwimLaneEntity swimLaneEntity, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        Optional<SwimLaneEntity> foundSwimLane = swimLaneEntityRepository.findByName(swimLaneEntity.getName());
        List<SwimLaneEntity> projectSwimLanes = project.getSwimLaneEntities();


        if (!foundSwimLane.isPresent()) {
            SwimLaneEntity newSwimLaneEntity = swimLaneEntityRepository.save(swimLaneEntity);
            System.out.println("swimLane entity id : " + swimLaneEntity.getId());
            projectSwimLanes.add(newSwimLaneEntity);
        } else {
            if (projectSwimLanes.contains(foundSwimLane.get())) {
                throw new DuplicateProjectSwimLaneException("Swim lane already exists in this project");
            }
            projectSwimLanes.add(foundSwimLane.get());
        }

        projectService.updateProject(project, userEmail);
        return createNewSwimLaneObject(swimLaneEntity, projectIdentifier);
    }


    public Optional<SwimLaneEntity> findSwimLaneByName(String swimLaneName) {
        return swimLaneEntityRepository.findByName(swimLaneName);
    }



    public boolean removeSwimLaneFromProject(String projectIdentifier, int swimLaneId, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        Optional<SwimLaneEntity> swimLaneEntity = swimLaneEntityRepository.findById(swimLaneId);
        try{
            List<ProjectTicketEntity> projectTicketEntities = project.getProjectTicketEntities().stream()
                    .filter(item -> item.getSwimLaneEntity().getId()==(swimLaneId)).collect(Collectors.toList());

            projectTicketEntities.forEach(ticket-> removeTicketFromProject(ticket, userEmail));
            Set<ProjectEntity> projectEntitySet = new HashSet<>();
            projectEntitySet.add(project);
            swimLaneEntityRepository.deleteSwimLaneEntityByName(project.getId(),swimLaneEntity.get().getId() );

        }catch(Exception e){
            log.error("There was an error deleting the swim lane: {}", e.getMessage());
            return false;
        }
        log.info("swim lane has been deleted successfully");
        return true;
    }

    private Map<String, SwimLane> createNewSwimLaneObject(SwimLaneEntity swimLaneEntity, String projectIdentifier) {

        SwimLane newSwimLane = SwimLane.builder().id(swimLaneEntity.getId()).title(swimLaneEntity.getName()).ticketIds(new ArrayList<>()).projectIdentifier(projectIdentifier).build();
        System.out.println("new swimlane: " + newSwimLane);
        Map<String, SwimLane> swimLaneMap = new HashMap<>();
        swimLaneMap.put(swimLaneEntity.getName(), newSwimLane);
        System.out.println("swimLaneMap " + swimLaneMap);
        return swimLaneMap;
    }

    private void removeTicketFromProject(ProjectTicketEntity projectTicketEntity, String userEmail) {
        ProjectEntity existingProject = userProjectValidator.getUserProject(projectTicketEntity.getProjectIdentifier(), userEmail);

        log.info("deleting ticket: " + projectTicketEntity.getId());
        projectTicketEntityRepository.deleteProjectTicket(projectTicketEntity.getId());
    }


}
