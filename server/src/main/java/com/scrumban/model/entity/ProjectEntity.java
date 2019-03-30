package com.scrumban.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scrumban.model.domain.Project;
import com.scrumban.model.domain.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "project")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name required.")
    private String projectName;

    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "Project description is needed")
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(updatable = false)
    private Date createdAt; //keeps track of whenever the object has been created or something has been updated.

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ProjectTicketEntity> projectTicketEntities;

    @Column
    private int currentTicketNumber;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "project_swimlane", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "swimlane_id", referencedColumnName = "id"))
    private List<SwimLaneEntity> swimLaneEntities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private String projectLeader;


    public static ProjectEntity from(Project project) {
        return ProjectEntity.builder()
                .id(project.getId())
                .projectName(project.getProjectName())
                .projectIdentifier(project.getProjectIdentifier())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())//keeps track of whenever the object has been created or something has been updated.
                .currentTicketNumber(project.getCurrentTicketNumber())
                .user(project.getUser())
                .projectLeader(project.getProjectLeader())
                .build();
    }

    @PrePersist
    protected void onCreate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String frmtdDate = dateFormat.format(date);
        this.createdAt = dateFormat.parse(frmtdDate);
        this.currentTicketNumber = 0;
    }
}
