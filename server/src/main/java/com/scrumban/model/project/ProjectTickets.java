package com.scrumban.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ProjectTickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false)
    private String projectSequence;
    @NotBlank(message = "please include project ticket summary")
    private String summary;
    @NotBlank(message = "please include at least one acceptance criteria")
    private String acceptanceCriteria;
    private String status;
    private String priority;

    @Column(updatable = false)
    private String projectIdentifier; //this is so you can ensure that when you are making changes to a project task, it is that ticket, part of that specific backlog, part of that specific project.

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "swimLane_id")
    private SwimLane swimLane;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

}