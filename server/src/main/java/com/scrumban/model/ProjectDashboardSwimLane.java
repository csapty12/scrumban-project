package com.scrumban.model;

import lombok.*;

import java.util.ArrayList;


@Data
@Builder
@ToString
public class ProjectDashboardSwimLane {
    private String title;
    private ArrayList<String> ticketIds;
}