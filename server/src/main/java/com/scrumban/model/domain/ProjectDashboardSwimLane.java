package com.scrumban.model.domain;

import lombok.*;

import java.util.ArrayList;


@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class ProjectDashboardSwimLane {
    private int id;
    private String title;
    private ArrayList<String> ticketIds;
    private String projectIdentifier;

}