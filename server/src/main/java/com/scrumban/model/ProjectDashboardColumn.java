package com.scrumban.model;

import lombok.*;

import java.util.ArrayList;


@Data
@Builder
@ToString
public class ProjectDashboardColumn {
    private String title;
    private ArrayList<String> ticketIds;
}