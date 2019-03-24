//package com.scrumban.service.project;
//
//import com.scrumban.exception.ProjectSwimLaneNotFoundException;
//import com.scrumban.model.domain.ProjectDashboard;
//import com.scrumban.model.domain.SwimLane;
//import com.scrumban.model.domain.User;
//import com.scrumban.model.entity.ProjectEntity;
//import com.scrumban.model.entity.ProjectTicketEntity;
//import com.scrumban.model.entity.SwimLaneEntity;
//import com.scrumban.repository.entity.ProjectTicketEntityRepository;
//import com.scrumban.validator.UserProjectValidator;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//@Slf4j
//public class ProjectTicketService {
//
//    private ProjectTicketEntityRepository projectTicketEntityRepository;
//    private SwimLaneService swimLaneService;
//    private UserProjectValidator userProjectValidator;
//
//    public ProjectTicketService(ProjectTicketEntityRepository projectTicketEntityRepository, SwimLaneService swimLaneService, UserProjectValidator userProjectValidator) {
//        this.projectTicketEntityRepository = projectTicketEntityRepository;
//        this.swimLaneService = swimLaneService;
//        this.userProjectValidator = userProjectValidator;
//    }
//
//    public ProjectDashboard getProjectDashboard(String projectIdentifier, String userEmail) {
//        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
//        return setupProjectDashboard(project);
//
//    }
//
//    public LinkedHashMap<String, ProjectTicketEntity> addProjectTicketToProject(String projectIdentifier,
//                                                                                String swimLaneName,
//                                                                                ProjectTicketEntity projectTicketEntity,
//                                                                                String userEmail) {
//
//        Optional<SwimLaneEntity> swimLane = swimLaneService.findSwimLaneByName(swimLaneName);
//        if (!swimLane.isPresent()) {
//            throw new ProjectSwimLaneNotFoundException("Swim lane with name: " + swimLaneName + " not found");
//        }
//
//        ProjectEntity existingProject = userProjectValidator.getUserProject(projectIdentifier, userEmail);
//
//        int currentTicketNumber = existingProject.getCurrentTicketNumber();
//        int incrementValue = 1;
//        String projectSequence = createTicketIdentifier(existingProject, currentTicketNumber, incrementValue);
//        projectTicketEntity.setProjectSequence(projectSequence);
//
//        projectTicketEntity.setProject(existingProject);
//        projectTicketEntity.setSwimLaneEntity(swimLane.get());
//        projectTicketEntity.setProjectIdentifier(existingProject.getProjectIdentifier());
//        projectTicketEntityRepository.save(projectTicketEntity);
//        LinkedHashMap<String, ProjectTicketEntity> singleProjectTicket = new LinkedHashMap<>();
//        singleProjectTicket.put(projectSequence, projectTicketEntity);
//        return singleProjectTicket;
//    }
//
//
//    public LinkedHashMap<String, ProjectTicketEntity> updateTicketInformation(ProjectTicketEntity projectTicketEntity, String projectIdentifier, String swimLaneName, String userEmail) {
//
//        ProjectEntity existingProject = userProjectValidator.getUserProject(projectIdentifier, userEmail);
//        Optional<SwimLaneEntity> swimLaneEntity = swimLaneService.findSwimLaneByName(swimLaneName);
//
//        if (!swimLaneEntity.isPresent()) {
//            throw new ProjectSwimLaneNotFoundException("Swim lane with name: " + swimLaneName + " not found");
//        }
//
//        projectTicketEntity.setProject(existingProject);
//        projectTicketEntity.setTicketNumberPosition(projectTicketEntity.getTicketNumberPosition());
//        projectTicketEntity.setSwimLaneEntity(swimLaneEntity.get());
//        projectTicketEntity.setProjectIdentifier(existingProject.getProjectIdentifier());
//
//        projectTicketEntityRepository.save(projectTicketEntity);
//
//        LinkedHashMap<String, ProjectTicketEntity> singleProjectTicket = new LinkedHashMap<>();
//        singleProjectTicket.put(projectTicketEntity.getProjectSequence(), projectTicketEntity);
//        return singleProjectTicket;
//    }
//
//
//    public void removeTicketFromProject(ProjectTicketEntity projectTicketEntity, String userEmail) {
//        ProjectEntity existingProject = userProjectValidator.getUserProject(projectTicketEntity.getProjectIdentifier(), userEmail);
//
//        log.info("deleting ticket: " + projectTicketEntity.getId());
//        projectTicketEntityRepository.deleteProjectTicket(projectTicketEntity.getId());
//
//    }
//
//    public void updateTicketOrderForSwimLane(String projectIdentifier, SwimLane swimLane, String userEmail) {
//        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
//
//        List<SwimLaneEntity> singleSwimLane = project.getSwimLaneEntities()
//                .stream()
//                .filter(swimLaneEntity -> swimLaneEntity.getName().equals(swimLane.getTitle())).collect(Collectors.toList());
//
//        List<ProjectTicketEntity> projectSwimLaneTickets = singleSwimLane.get(0)
//                .getProjectTicketEntities()
//                .stream()
//                .filter(ticket -> ticket.getProject().getProjectIdentifier()
//                        .equals(projectIdentifier)).collect(Collectors.toList()
//                );
//
//        projectSwimLaneTickets.forEach(ticket ->
//                updateTicketPositionInSwimLane(swimLane, ticket));
//    }
//
//
//    @Transactional
//    public void updateTicketSwimLane(String projectIdentifier, List<SwimLane> swimLanes, String userEmail) {
//        userProjectValidator.getUserProject(projectIdentifier, userEmail);
//
//        swimLanes.forEach(swimLane -> {
//            List<String> ticketIds = swimLane.getTicketIds();
//            Optional<SwimLaneEntity> swimLaneEntity = swimLaneService.findSwimLaneByName(swimLane.getTitle());
//            ticketIds.forEach(ticketId -> {
//                ProjectTicketEntity projectTicketEntity = projectTicketEntityRepository.findByProjectSequence(ticketId);
//                projectTicketEntity.setSwimLaneEntity(swimLaneEntity.get());
//                projectTicketEntityRepository.save(projectTicketEntity);
//            });
//        });
//    }
//
//    public void updateTicketPositionInNewSwimLane(String projectIdentifier, List<SwimLane> swimLanes, String userEmail) {
//        ProjectEntity project = userProjectValidator.getUserProject(projectIdentifier, userEmail);
//        User user = project.getUser();
//
//        swimLanes.get(0).getTicketIds().forEach(ticket -> {
//            ProjectTicketEntity projectTicketEntity = projectTicketEntityRepository.findByProjectSequence(ticket);
//            if (projectTicketEntity.getProject().getUser().getId().equals(user.getId())) {
//                updateTicketPositionInSwimLane(swimLanes.get(0), projectTicketEntity);
//            }
//        });
//        swimLanes.get(1).getTicketIds().forEach(ticket -> {
//            ProjectTicketEntity projectTicketEntity = projectTicketEntityRepository.findByProjectSequence(ticket);
//            if (projectTicketEntity.getProject().getUser().getId().equals(user.getId())) {
//                updateTicketPositionInSwimLane(swimLanes.get(1), projectTicketEntity);
//            }
//        });
//    }
//
//    private ArrayList<String> getTicketIds(List<ProjectTicketEntity> allProjectTicketEntities, String columnName) {
//        ArrayList<String> projectTaskIds = new ArrayList<>();
//
//        allProjectTicketEntities.forEach(projectTicket -> {
//            if (projectTicket.getSwimLaneEntity().getName().equals(columnName)) {
//
//                projectTaskIds.add(projectTicket.getProjectSequence());
//            }
//        });
//        return projectTaskIds;
//    }
//
//    private void updateTicketPositionInSwimLane(SwimLane swimLane, ProjectTicketEntity ticket) {
//
//        int indexOfTicket = swimLane.getTicketIds().indexOf(ticket.getProjectSequence());
//        ticket.setTicketNumberPosition(++indexOfTicket);
//        projectTicketEntityRepository.save(ticket);
//    }
//
//    private List<LinkedHashMap<String, ProjectTicketEntity>> insertAllTickets(List<ProjectTicketEntity> allProjectTicketEntities) {
//        if (allProjectTicketEntities.size() == 0) {
//            return new ArrayList<>();
//        }
//        List<LinkedHashMap<String, ProjectTicketEntity>> projectTicketList = new ArrayList<>();
//        LinkedHashMap<String, ProjectTicketEntity> projectTicketMap = new LinkedHashMap<>();
//        allProjectTicketEntities.sort(Comparator.comparingInt(ProjectTicketEntity::getTicketNumberPosition));
//
//        allProjectTicketEntities.forEach(ticket -> projectTicketMap.put(ticket.getProjectSequence(), ticket));
//        projectTicketList.add(projectTicketMap);
//        return projectTicketList;
//    }
//
//    private String getAcronymFromProjectIdentifier(String projectIdentifier) {
//        String initials = Arrays.stream(projectIdentifier.split("-"))
//                .map(s -> s.substring(0, 1))
//                .collect(Collectors.joining());
//        return initials.toUpperCase();
//    }
//
//    private List<Map<String, SwimLane>> addSwimLaneWithTickets(ProjectEntity projectEntity) {
//        List<SwimLaneEntity> swimLaneNames = new ArrayList<>();
//        getProjectSwimLanes(projectEntity, swimLaneNames);
//
//        List<ProjectTicketEntity> allProjectTicketEntities = projectEntity.getProjectTicketEntities();
//        List<Map<String, SwimLane>> listOfColumns = new ArrayList<>();
//        insertTicketsIntoSwimLanes(swimLaneNames, allProjectTicketEntities, listOfColumns, projectEntity.getProjectIdentifier());
//        return listOfColumns;
//    }
//
//    private void insertTicketsIntoSwimLanes(List<SwimLaneEntity> swimLaneEntities, List<ProjectTicketEntity> allProjectTicketEntities, List<Map<String, SwimLane>> swimLanes, String projectIdentifier) {
//        swimLaneEntities.forEach(swimLane -> {
//            Map<String, SwimLane> projectDashboardSwimLanes = new HashMap<>();
//            projectDashboardSwimLanes.put(swimLane.getName(), createSwimLaneAndInsertTasks(swimLane, allProjectTicketEntities, projectIdentifier));
//            swimLanes.add(projectDashboardSwimLanes);
//        });
//    }
//
//    private SwimLane createSwimLaneAndInsertTasks(SwimLaneEntity swimLane, List<ProjectTicketEntity> swimLaneTickets, String projectIdentifier) {
//        return SwimLane
//                .builder()
//                .id(swimLane.getId())
//                .projectIdentifier(projectIdentifier)
//                .title(swimLane.getName())
//                .ticketIds(getTicketIds(swimLaneTickets, swimLane.getName())).build();
//    }
//
//    private void getProjectSwimLanes(ProjectEntity projectEntity, List<SwimLaneEntity> swimLanes) {
//        projectEntity.getSwimLaneEntities().forEach(swimLane -> {
//            swimLanes.add(swimLane);
//        });
//    }
//
//    private ProjectDashboard setupProjectDashboard(ProjectEntity project) {
//        List<ProjectTicketEntity> allProjectTicketEntities = project.getProjectTicketEntities();
//        ProjectDashboard projectDashboard = new ProjectDashboard();
//
//        List<LinkedHashMap<String, ProjectTicketEntity>> allTickets = insertAllTickets(allProjectTicketEntities);
//        projectDashboard.setTickets(allTickets);
//        List<Map<String, SwimLane>> swimLanesAndTicketReferences = addSwimLaneWithTickets(project);
//        projectDashboard.setSwimLanes(swimLanesAndTicketReferences);
//        List<String> swimLaneOrder = new ArrayList<>();
//        swimLanesAndTicketReferences.forEach(column -> {
//            for (String key : column.keySet()) {
//                swimLaneOrder.add(key);
//            }
//        });
//        projectDashboard.setSwimLaneOrder(swimLaneOrder);
//        return projectDashboard;
//    }
//
//    private String createTicketIdentifier(ProjectEntity existingProject, int currentTicketNumber, int incrementValue) {
//        String acronym = getAcronymFromProjectIdentifier(existingProject.getProjectIdentifier());
//        int newProjectTicketSequenceValue = currentTicketNumber + incrementValue;
//        return acronym + "-" + newProjectTicketSequenceValue;
//    }
//
//}
