package com.scrumban.service.project;

import com.scrumban.exception.ProjectSwimLaneNotFoundException;
import com.scrumban.model.domain.ProjectDashboard;
import com.scrumban.model.domain.ProjectDashboardSwimLane;
import com.scrumban.model.domain.SwimLane;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.validator.UserProjectValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProjectTicketService {

    private ProjectTicketRepository projectTicketRepository;
    private SwimLaneService swimLaneService;
    private UserProjectValidator userProjectValidator;

    public ProjectTicketService(ProjectTicketRepository projectTicketRepository, SwimLaneService swimLaneService, UserProjectValidator userProjectValidator) {
        this.projectTicketRepository = projectTicketRepository;
        this.swimLaneService = swimLaneService;
        this.userProjectValidator = userProjectValidator;
    }

    public ProjectDashboard getProjectDashboard(String projectIdentifier, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        return setupProjectDashboard(project);

    }

    public LinkedHashMap<String, ProjectTicket> addProjectTicketToProject(String projectIdentifier,
                                                                          String swimLaneName,
                                                                          ProjectTicket projectTicket,
                                                                          String userEmail) {

        Optional<SwimLaneEntity> swimLane = swimLaneService.findSwimLaneByName(swimLaneName);
        if (!swimLane.isPresent()) {
            throw new ProjectSwimLaneNotFoundException("Swim lane with name: " + swimLaneName + " not found");
        }

        ProjectEntity existingProject = userProjectValidator.getUserProject(projectIdentifier, userEmail);

        int currentTicketNumber = existingProject.getCurrentTicketNumber();
        int incrementValue = 1;
        String projectSequence = createTicketIdentifier(existingProject, currentTicketNumber, incrementValue);
        projectTicket.setProjectSequence(projectSequence);

        projectTicket.setProject(existingProject);
        projectTicket.setSwimLaneEntity(swimLane.get());
        projectTicket.setProjectIdentifier(existingProject.getProjectIdentifier());
        projectTicketRepository.save(projectTicket);
        LinkedHashMap<String, ProjectTicket> singleProjectTicket = new LinkedHashMap<>();
        singleProjectTicket.put(projectSequence, projectTicket);
        return singleProjectTicket;
    }


    public LinkedHashMap<String, ProjectTicket> updateTicketInformation(ProjectTicket projectTicket, String projectIdentifier, String swimLaneName, String userEmail) {

        ProjectEntity existingProject = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        Optional<SwimLaneEntity> swimLaneEntity = swimLaneService.findSwimLaneByName(swimLaneName);

        if (!swimLaneEntity.isPresent()) {
            throw new ProjectSwimLaneNotFoundException("Swim lane with name: " + swimLaneName + " not found");
        }

        projectTicket.setProject(existingProject);
        projectTicket.setTicketNumberPosition(projectTicket.getTicketNumberPosition());
        projectTicket.setSwimLaneEntity(swimLaneEntity.get());
        projectTicket.setProjectIdentifier(existingProject.getProjectIdentifier());

        projectTicketRepository.save(projectTicket);

        LinkedHashMap<String, ProjectTicket> singleProjectTicket = new LinkedHashMap<>();
        singleProjectTicket.put(projectTicket.getProjectSequence(), projectTicket);
        return singleProjectTicket;
    }


    public void removeTicketFromProject(ProjectTicket projectTicket, String userEmail) {
        ProjectEntity existingProject = userProjectValidator.getUserProject(projectTicket.getProjectIdentifier(), userEmail);

        log.info("deleting ticket: " + projectTicket.getId());
        projectTicketRepository.deleteProjectTicket(projectTicket.getId());

    }

    public void updateTicketOrderForSwimLane(String projectIdentifier, SwimLane swimLane, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);

        List<SwimLaneEntity> singleSwimLane = project.getSwimLaneEntities()
                .stream()
                .filter(swimLaneEntity -> swimLaneEntity.getName().equals(swimLane.getTitle())).collect(Collectors.toList());

        List<ProjectTicket> projectSwimLaneTickets = singleSwimLane.get(0)
                .getProjectTickets()
                .stream()
                .filter(ticket -> ticket.getProject().getProjectIdentifier()
                        .equals(projectIdentifier)).collect(Collectors.toList()
                );

        projectSwimLaneTickets.forEach(ticket ->
                updateTicketPositionInSwimLane(swimLane, ticket));
    }


    @Transactional
    public void updateTicketSwimLane(String projectIdentifier, List<SwimLane> swimLanes, String userEmail) {
        userProjectValidator.getUserProject(projectIdentifier, userEmail);

        swimLanes.forEach(swimLane -> {
            List<String> ticketIds = swimLane.getTicketIds();
            Optional<SwimLaneEntity> swimLaneEntity = swimLaneService.findSwimLaneByName(swimLane.getTitle());
            ticketIds.forEach(ticketId -> {
                ProjectTicket projectTicket = projectTicketRepository.findByProjectSequence(ticketId);
                projectTicket.setSwimLaneEntity(swimLaneEntity.get());
                projectTicketRepository.save(projectTicket);
            });
        });
    }

    public void updateTicketPositionInNewSwimLane(String projectIdentifier, List<SwimLane> swimLanes, String userEmail) {
        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
        User user = project.getUser();

        swimLanes.get(0).getTicketIds().forEach(ticket -> {
            ProjectTicket projectTicket = projectTicketRepository.findByProjectSequence(ticket);
            if (projectTicket.getProject().getUser().getId().equals(user.getId())) {
                updateTicketPositionInSwimLane(swimLanes.get(0), projectTicket);
            }
        });
        swimLanes.get(1).getTicketIds().forEach(ticket -> {
            ProjectTicket projectTicket = projectTicketRepository.findByProjectSequence(ticket);
            if (projectTicket.getProject().getUser().getId().equals(user.getId())) {
                updateTicketPositionInSwimLane(swimLanes.get(1), projectTicket);
            }
        });
    }

    private ProjectDashboardSwimLane createSwimLaneAndInsertTasks(String columnName, List<ProjectTicket> allProjectTickets) {
        return ProjectDashboardSwimLane
                .builder()
                .title(columnName)
                .ticketIds(getTicketIds(allProjectTickets, columnName)).build();
    }

    private ArrayList<String> getTicketIds(List<ProjectTicket> allProjectTickets, String columnName) {
        ArrayList<String> projectTaskIds = new ArrayList<>();

        allProjectTickets.forEach(projectTicket -> {
            if (projectTicket.getSwimLaneEntity().getName().equals(columnName)) {

                projectTaskIds.add(projectTicket.getProjectSequence());
            }
        });
        return projectTaskIds;
    }

    private void updateTicketPositionInSwimLane(SwimLane swimLane, ProjectTicket ticket) {

        int indexOfTicket = swimLane.getTicketIds().indexOf(ticket.getProjectSequence());
        ticket.setTicketNumberPosition(++indexOfTicket);
        projectTicketRepository.save(ticket);
    }

    private List<LinkedHashMap<String, ProjectTicket>> insertAllTickets(List<ProjectTicket> allProjectTickets) {
        if (allProjectTickets.size() == 0) {
            return new ArrayList<>();
        }
        List<LinkedHashMap<String, ProjectTicket>> projectTicketList = new ArrayList<>();
        LinkedHashMap<String, ProjectTicket> projectTicketMap = new LinkedHashMap<>();
        allProjectTickets.sort(Comparator.comparingInt(ProjectTicket::getTicketNumberPosition));

        allProjectTickets.forEach(ticket -> projectTicketMap.put(ticket.getProjectSequence(), ticket));
        projectTicketList.add(projectTicketMap);
        return projectTicketList;
    }

    private String getAcronymFromProjectIdentifier(String projectIdentifier) {
        String initials = Arrays.stream(projectIdentifier.split("-"))
                .map(s -> s.substring(0, 1))
                .collect(Collectors.joining());
        return initials.toUpperCase();
    }

    private List<Map<String, ProjectDashboardSwimLane>> addSwimLaneWithTickets(ProjectEntity projectEntity) {
        List<String> swimLaneNames = new ArrayList<>();
        getProjectSwimLanes(projectEntity, swimLaneNames);

        List<ProjectTicket> allProjectTickets = projectEntity.getProjectTickets();
        List<Map<String, ProjectDashboardSwimLane>> listOfColumns = new ArrayList<>();
        insertTicketsIntoSwimLanes(swimLaneNames, allProjectTickets, listOfColumns);
        return listOfColumns;
    }

    private void insertTicketsIntoSwimLanes (List<String> swimLaneNames, List<ProjectTicket> allProjectTickets, List<Map<String, ProjectDashboardSwimLane>> swimLanes) {
        swimLaneNames.forEach(swimLaneName -> {
            Map<String, ProjectDashboardSwimLane> projectDashboardSwimLanes = new HashMap<>();
            projectDashboardSwimLanes.put(swimLaneName, createSwimLaneAndInsertTasks(swimLaneName, allProjectTickets));
            swimLanes.add(projectDashboardSwimLanes);
        });
    }

    private void getProjectSwimLanes(ProjectEntity projectEntity, List<String> swimLaneNames) {
        projectEntity.getSwimLaneEntities().forEach(swimLane -> {
            swimLaneNames.add(swimLane.getName());
        });
    }

    private ProjectDashboard setupProjectDashboard(ProjectEntity project) {
        List<ProjectTicket> allProjectTickets = project.getProjectTickets();
        ProjectDashboard projectDashboard = new ProjectDashboard();

        List<LinkedHashMap<String, ProjectTicket>> allTickets = insertAllTickets(allProjectTickets);
        projectDashboard.setTickets(allTickets);
        List<Map<String, ProjectDashboardSwimLane>> swimLanesAndTicketReferences = addSwimLaneWithTickets(project);
        projectDashboard.setSwimLanes(swimLanesAndTicketReferences);
        List<String> swimLaneOrder = new ArrayList<>();
        swimLanesAndTicketReferences.forEach(column -> {
            for (String key : column.keySet()) {
                swimLaneOrder.add(key);
            }
        });
        projectDashboard.setSwimLaneOrder(swimLaneOrder);
        return projectDashboard;
    }

    private String createTicketIdentifier(ProjectEntity existingProject, int currentTicketNumber, int incrementValue) {
        String acronym = getAcronymFromProjectIdentifier(existingProject.getProjectIdentifier());
        int newProjectTicketSequenceValue = currentTicketNumber + incrementValue;
        return acronym + "-" + newProjectTicketSequenceValue;
    }

}
