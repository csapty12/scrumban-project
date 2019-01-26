package com.scrumban.model.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="swimLane")
public class SwimLaneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "swimLaneEntities",  cascade = CascadeType.DETACH)
    private Set<ProjectEntity> projectEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "swimLaneEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectTicket> projectTickets;



}
