package com.scrumban.service.project;

import com.scrumban.exception.DuplicateProjectSwimLaneException;
import com.scrumban.model.domain.SwimLane;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.repository.SwimLaneRepository;
import com.scrumban.validator.UserProjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@Service
@Slf4j
public class SwimLaneService {

    private ProjectService projectService;
    private SwimLaneRepository swimLaneRepository;
    private UserProjectValidator userProjectValidator;
    private ProjectTicketRepository projectTicketRepository;

    public SwimLaneService(ProjectService projectService, SwimLaneRepository swimLaneRepository, UserProjectValidator userProjectValidator, ProjectTicketRepository projectTicketRepository) {
        this.projectService = projectService;
        this.swimLaneRepository = swimLaneRepository;
        this.userProjectValidator = userProjectValidator;
        this.projectTicketRepository = projectTicketRepository;
    }

    public Map<String, SwimLane> addSwimLaneToProject(String projectIdentifier, SwimLaneEntity swimLaneEntity, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        Optional<SwimLaneEntity> foundSwimLane = swimLaneRepository.findByName(swimLaneEntity.getName());
        List<SwimLaneEntity> projectSwimLanes = project.getSwimLaneEntities();
        insertSwimLaneToProject(swimLaneEntity, foundSwimLane, projectSwimLanes);
        projectService.updateProject(project, userEmail);
        return createNewSwimLaneObject(swimLaneEntity);
    }


    public Optional<SwimLaneEntity> findSwimLaneByName(String swimLaneName) {
        return swimLaneRepository.findByName(swimLaneName);
    }



    public boolean removeSwimLaneFromProject(String projectIdentifier, String swimLaneId, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        Optional<SwimLaneEntity> swimLaneEntity = swimLaneRepository.findByName(swimLaneId);

        try{
            List<ProjectTicket> projectTickets = project.getProjectTickets().stream()
                    .filter(item -> item.getSwimLaneEntity().getName().equals(swimLaneId)).collect(Collectors.toList());

            projectTickets.forEach(ticket-> removeTicketFromProject(ticket, userEmail));
            Set<ProjectEntity> projectEntitySet = new HashSet<>();
            projectEntitySet.add(project);
            swimLaneRepository.deleteSwimLaneEntityByName(project.getId(),swimLaneEntity.get().getId() );

        }catch(Exception e){
            log.error("There was an error deleting the swim lane: {}", e.getMessage());
            return false;
        }
        log.info("swim lane has been deleted successfuly");
        return true;
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

    private void removeTicketFromProject(ProjectTicket projectTicket, String userEmail) {
        ProjectEntity existingProject = userProjectValidator.getUserProject(projectTicket.getProjectIdentifier(), userEmail);

        log.info("deleting ticket: " + projectTicket.getId());
        projectTicketRepository.deleteProjectTicket(projectTicket.getId());
    }


}
