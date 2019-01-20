package com.scrumban.model.project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="projectTickets")
public class ProjectTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String projectSequence;
    @NotBlank(message = "please include projectEntity ticket summary")
    private String summary;
    @NotBlank(message = "please include at least one acceptance criteria")
    private String acceptanceCriteria;
    private String priority;


    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(updatable = false)
    private Date createdAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "swimLane_id")
    private SwimLaneEntity swimLaneEntity;


    private String projectIdentifier;
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
        int totalNumberOfTickets = this.project.getCurrentTicketNumber();
        this.project.setCurrentTicketNumber(totalNumberOfTickets+1);

    }

}