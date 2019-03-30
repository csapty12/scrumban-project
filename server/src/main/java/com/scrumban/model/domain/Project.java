package com.scrumban.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.ProjectTicketEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
@JsonInclude(NON_EMPTY)
public class Project {

    private Long id;
    private String projectName;
    private String projectIdentifier;
    private String description;
    private Date createdAt;
    @JsonIgnore
    private List<ProjectTicket> projectTickets;
    private int currentTicketNumber;
    @JsonIgnore
    private List<SwimLaneEntity> swimLaneEntities;
    @JsonIgnore
    private User user;
    private String projectLeader;

    public static Project from(ProjectEntity projectEntity) {
        return Project.builder()
                .id(projectEntity.getId())
                .projectName(projectEntity.getProjectName())
                .projectIdentifier(projectEntity.getProjectIdentifier())
                .description(projectEntity.getDescription())
                .createdAt(projectEntity.getCreatedAt())
                .projectTickets(getProjectTickets(projectEntity))
                .currentTicketNumber(projectEntity.getCurrentTicketNumber())
                .swimLaneEntities(projectEntity.getSwimLaneEntities())
                .projectLeader(projectEntity.getProjectLeader())
                .user(projectEntity.getUser())
                .build();
    }

    private static List<ProjectTicket> getProjectTickets(ProjectEntity projectEntity) {

       return projectEntity.getProjectTicketEntities().stream().map(projectTicketEntity ->
                ProjectTicket
                        .builder()
                        .id(projectTicketEntity.getId())
                        .projectSequence(projectTicketEntity.getProjectSequence())
                        .summary(projectTicketEntity.getSummary())
                        .acceptanceCriteria(projectTicketEntity.getAcceptanceCriteria())
                        .complexity(projectTicketEntity.getComplexity())
                        .priority(projectTicketEntity.getPriority())
                        .createdAt(projectTicketEntity.getCreatedAt())
                        .project(projectTicketEntity.getProject())
                        .swimLaneEntity(projectTicketEntity.getSwimLaneEntity())
                        .ticketNumberPosition(projectTicketEntity.getTicketNumberPosition())
                        .projectIdentifier(projectTicketEntity.getProjectIdentifier())
                        .build()).collect(Collectors.toList());
    }
}
