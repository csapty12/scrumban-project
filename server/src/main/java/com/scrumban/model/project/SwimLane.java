package com.scrumban.model.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="swimLane")
public class SwimLane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "swimLanes")
    private Set<Project> projects;

    @JsonIgnore
    @OneToMany(mappedBy = "swimLane", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ProjectTicket> projectTickets;
}
