package com.scrumban.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0; //the sequence of project tasks in each backlog
    private String projectIdentifier; //shares the same Identifier as a project.

    //One to One relationship between project and backlog - one project can only have one backlog
    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;

    //one to many relationship between backlog and project tasks - one backlog can have many tasks
    @OneToMany(cascade = ALL, fetch = EAGER, mappedBy = "backlog")
    private List<ProjectTask> projectTaskList = new ArrayList<>();

}
