package com.scrumban.model.domain;

import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.ProjectTicketEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import lombok.*;

import java.util.Date;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class ProjectTicket {

    private Long id;
    private String projectSequence;
    private String summary;
    private String acceptanceCriteria;
    private int complexity;
    private String priority;
    private Date createdAt;
    private ProjectEntity project;
    private SwimLaneEntity swimLaneEntity;
    private int ticketNumberPosition;
    private String projectIdentifier;

    public ProjectTicket from(ProjectTicketEntity projectTicketEntity) {
        return ProjectTicket.builder()
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
                .build();
    }
}
