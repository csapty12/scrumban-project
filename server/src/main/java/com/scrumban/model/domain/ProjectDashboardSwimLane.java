package com.scrumban.model.domain;

import lombok.*;

import java.util.ArrayList;


@Data
@Builder
@ToString
public class ProjectDashboardSwimLane {
    private String title;
    private ArrayList<String> ticketIds;
}