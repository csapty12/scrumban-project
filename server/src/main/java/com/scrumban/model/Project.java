package com.scrumban.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

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

    @JsonFormat(pattern = "yyyy-mm-dd")
    @NotNull(message = "Estimated start date required.")
    private Date startDate; //start date of project

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date createdAt; //keeps track of whenever the object has been created or something has been updated.

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProjectTickets> projectTickets;

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
}
