package com.scrumban.model.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Project name required.")
    private String projectName;

    @NotBlank(message = "Project identifier required.")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;

    @NotBlank(message = "project description is needed")
    private String description;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(updatable = false)
    private Date createdAt; //keeps track of whenever the object has been created or something has been updated.

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ProjectTicket> projectTickets = new ArrayList<>();

    @Column
    private int currentTicketNumber;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "project_swimlane", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "swimlane_id", referencedColumnName = "id"))
    private List<SwimLane> swimLanes = new ArrayList<>();

    @PrePersist
    protected void onCreate() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String frmtdDate = dateFormat.format(date);
        this.createdAt = dateFormat.parse(frmtdDate);
        this.currentTicketNumber = 0;
    }
}
