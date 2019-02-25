package com.scrumban.service.project;

import com.scrumban.exception.ProjectIdentifierException;
import com.scrumban.exception.ProjectSwimLaneNotFoundException;
import com.scrumban.model.ProjectDashboardColumn;
import com.scrumban.model.SwimLane;
import com.scrumban.model.Tickets;
import com.scrumban.model.domain.User;
import com.scrumban.model.project.entity.ProjectEntity;
import com.scrumban.model.project.entity.ProjectTicket;
import com.scrumban.model.project.entity.SwimLaneEntity;
import com.scrumban.repository.ProjectTicketRepository;
import com.scrumban.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
public class ProjectTicketService {

    private ProjectTicketRepository projectTicketRepository;
    private ProjectService projectService;
    private SwimLaneService swimLaneService;
    private UserRepository userRepository;

    public ProjectTicketService(ProjectTicketRepository projectTicketRepository, ProjectService projectService, SwimLaneService swimLaneService, UserRepository userRepository) {
        this.projectTicketRepository = projectTicketRepository;
        this.projectService = projectService;
        this.swimLaneService = swimLaneService;
        this.userRepository = userRepository;
    }

    public Tickets getProjectDashboard(ProjectEntity project, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent() && (project.getUser().getId().equals(user.get().getId()))){
            List<ProjectTicket> allProjectTickets = project.getProjectTickets();
            Tickets tickets = new Tickets();
            List<LinkedHashMap<String, ProjectTicket>> allTickets = insertAllTickets(allProjectTickets);
            tickets.setTickets(allTickets);
            List<Map<String, ProjectDashboardColumn>> swimLanesAndTicketReferences = addSwimLaneWithTickets(project);
            tickets.setSwimLanes(swimLanesAndTicketReferences);
            List<String> swimLaneOrder = new ArrayList<>();
            swimLanesAndTicketReferences.forEach(column -> {
                for (String key : column.keySet()) {
                    swimLaneOrder.add(key);
                }
            });
            tickets.setSwimLaneOrder(swimLaneOrder);

            return tickets;
        }
        throw new UsernameNotFoundException("User not associated with project");


    }

    public LinkedHashMap<String, ProjectTicket> addProjectTicketToProject(ProjectEntity projectEntity,
                                                                          String swimLaneName,
                                                                          ProjectTicket projectTicket,
                                                                          String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent() && (projectEntity.getUser().getId().equals(user.get().getId()))){
            Optional<SwimLaneEntity> swimLaneEntity = swimLaneService.findSwimLaneByName(swimLaneName);
            if (!swimLaneEntity.isPresent()) {
                throw new ProjectSwimLaneNotFoundException("Swim lane with name: " + swimLaneName + " not found");
            }

            int currentTicketNumber = projectEntity.getCurrentTicketNumber();
            int incrementValue = 1;
            String acronym = getAcronymFromProjectIdentifier(projectEntity.getProjectIdentifier());
            int newProjectTicketSequenceValue = currentTicketNumber + incrementValue;
            String projectSequence = acronym + "-" + newProjectTicketSequenceValue;
            projectTicket.setProjectSequence(projectSequence);


            projectTicket.setProject(projectEntity);
            projectTicket.setSwimLaneEntity(swimLaneEntity.get());
            projectTicket.setProjectIdentifier(projectEntity.getProjectIdentifier());
            projectTicketRepository.save(projectTicket);

            LinkedHashMap<String, ProjectTicket> singleProjectTicket = new LinkedHashMap<>();
            singleProjectTicket.put(projectSequence, projectTicket);

            return singleProjectTicket;
        }
        throw new UsernameNotFoundException("User not found");


    }


    public void removeTicketFromProject(ProjectTicket projectTicket, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent() && projectTicket.getProject().getUser().getId().equals(user.get().getId())){
            System.out.println("ticket id " + projectTicket.getId());
            projectTicketRepository.deleteProjectTicket(projectTicket.getId());
        }
    }

    public void updateTicketOrderForSwimLane(String projectIdentifier, SwimLane swimLane, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            Optional<ProjectEntity> project = projectService.tryToFindProject(projectIdentifier, userEmail);
            if (!project.isPresent()) {
                throw new ProjectIdentifierException(format("Project with Id: %s not found.", projectIdentifier));
            }
            List<SwimLaneEntity> singleSwimLane = project.get().getSwimLaneEntities()
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
        throw new UsernameNotFoundException("User not found");
    }

    @Transactional
    public void updateTicketSwimLane(String projectIdentifier, List<SwimLane> swimLanes, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            Optional<ProjectEntity> project = projectService.tryToFindProject(projectIdentifier, userEmail);
            if (!project.isPresent()) {
                throw new ProjectIdentifierException(format("Project with Id: %s not found.", projectIdentifier));
            }

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
        throw new UsernameNotFoundException("User not found");

    }

    public void updateTicketPositionInNewSwimLane(List<SwimLane> swimLanes, String userEmail) {
        Optional<User> user = userRepository.findByEmail(userEmail);
        if(user.isPresent()){
            swimLanes.get(0).getTicketIds().forEach(ticket -> {
                ProjectTicket projectTicket = projectTicketRepository.findByProjectSequence(ticket);
                if(projectTicket.getProject().getUser().getId().equals(user.get().getId())) {
                    updateTicketPositionInSwimLane(swimLanes.get(0), projectTicket);
                }
            });
            swimLanes.get(1).getTicketIds().forEach(ticket -> {
                ProjectTicket projectTicket = projectTicketRepository.findByProjectSequence(ticket);
                if(projectTicket.getProject().getUser().getId().equals(user.get().getId())) {
                    updateTicketPositionInSwimLane(swimLanes.get(1), projectTicket);
                }
            });
        }


    }

    private ProjectDashboardColumn createColumnAndInsertTasks(String columnName, List<ProjectTicket> allProjectTickets) {
        return ProjectDashboardColumn
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

    private List<Map<String, ProjectDashboardColumn>> addSwimLaneWithTickets(ProjectEntity projectEntity) {

        List<String> columnNames = new ArrayList<>();

        projectEntity.getSwimLaneEntities().forEach(column -> {

            columnNames.add(column.getName());
        });


        List<ProjectTicket> allProjectTickets = projectEntity.getProjectTickets();


        List<Map<String, ProjectDashboardColumn>> listOfColumns = new ArrayList<>();
        for (String columnName : columnNames) {
            Map<String, ProjectDashboardColumn> projectDashboardColumn = new HashMap<>();
            projectDashboardColumn.put(columnName, createColumnAndInsertTasks(columnName, allProjectTickets));
            listOfColumns.add(projectDashboardColumn);
        }
        return listOfColumns;

    }
}
