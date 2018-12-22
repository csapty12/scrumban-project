package com.scrumban.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProjectTask {

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
    //many to one with backlog- many tasks belong to one backlog

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "backlog_id", nullable = false, updatable = false)
    @JsonIgnore //prevents infinite recursion
    private Backlog backlog;



    @Column(updatable = false)
    private String projectIdentifier; //this is so you can ensure that when you are making changes to a project task, it is that ticket, part of that specific backlog, part of that specific project.

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date createdAt;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
