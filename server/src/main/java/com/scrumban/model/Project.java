package com.scrumban.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Project name required.")
    private String projectName;
    @NotBlank(message = "Project identifier required.")
    @Size(min = 4, message = "please use at least 4 characters for the identifier e.g. ABC-1")
    @Column(updatable = false, unique = true)
    private String projectIdentifier;
    @NotBlank(message = "project description is needed")
    private String description;
    @JsonFormat(pattern = "yyyy-mm-dd")
    @NotNull(message = "Estimated start date required.")
    private Date startDate; //start date of project
    @JsonFormat(pattern = "yyyy-mm-dd" )
    @NotNull(message = "Estimated end date required.")
    private Date endDate; //end date of project.

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(updatable = false)
    private Date createdAt; //keeps track of whenever the object has been created or something has been updated.
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date updatedAt;

    @OneToOne(fetch = EAGER, cascade = CascadeType.ALL, mappedBy = "project") //cascade type - if you delete a project, then everything inside the backlog and all its children are destroyed too.
//    @JsonIgnore
    private Backlog backlog; //a project has only one backlog

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = new Date();
    }
}
