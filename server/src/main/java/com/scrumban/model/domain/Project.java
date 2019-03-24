package com.scrumban.model.domain;

import com.scrumban.model.entity.ProjectEntity;
import com.scrumban.model.entity.ProjectTicketEntity;
import com.scrumban.model.entity.SwimLaneEntity;
import lombok.*;

import java.util.Date;
import java.util.List;

@Builder
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Project {

    private Long id;
    private String projectName;
    private String projectIdentifier;
    private String description;
    private Date createdAt; //keeps track of whenever the object has been created or something has been updated.
    private List<ProjectTicketEntity> projectTicketEntities;
    private int currentTicketNumber;
    private List<SwimLaneEntity> swimLaneEntities;
    private User user;
    private String projectLeader;

    public Project from(ProjectEntity projectEntity) {
        return Project.builder()
                .id(projectEntity.getId())
                .projectName(projectEntity.getProjectName())
                .projectIdentifier(projectEntity.getProjectIdentifier())
                .description(projectEntity.getDescription())
                .createdAt(projectEntity.getCreatedAt())
                .projectTicketEntities(projectEntity.getProjectTicketEntities())
                .currentTicketNumber(projectEntity.getCurrentTicketNumber())
                .swimLaneEntities(projectEntity.getSwimLaneEntities())
                .user(projectEntity.getUser())
                .projectLeader(projectEntity.getProjectLeader())
                .build();
    }
}
