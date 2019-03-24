package com.scrumban.model.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.ProjectTicketEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

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
    private List<ProjectTicketEntity> projectTicketEntities;
    private int currentTicketNumber;
    private List<SwimLaneEntity> swimLaneEntities;
    private User user;
    private String projectLeader;

    public static Project from(ProjectEntity projectEntity) {
        return Project.builder()
                .id(projectEntity.getId())
                .projectName(projectEntity.getProjectName())
                .projectIdentifier(projectEntity.getProjectIdentifier())
                .description(projectEntity.getDescription())
                .createdAt(projectEntity.getCreatedAt())
                .projectTicketEntities(projectEntity.getProjectTicketEntities())
                .currentTicketNumber(projectEntity.getCurrentTicketNumber())
                .swimLaneEntities(projectEntity.getSwimLaneEntities())
                .projectLeader(projectEntity.getProjectLeader())
                .build();
    }
}
