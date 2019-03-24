package com.scrumban.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="swimLane")
@AllArgsConstructor
@Builder
public class SwimLaneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message="Please provide a swim lane name.")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "swimLaneEntities",  cascade = CascadeType.DETACH)
    private Set<ProjectEntity> projectEntities;

    @JsonIgnore
    @OneToMany(mappedBy = "swimLaneEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProjectTicketEntity> projectTicketEntities;

}
