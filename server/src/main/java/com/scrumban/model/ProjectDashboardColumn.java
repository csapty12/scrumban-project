package com.scrumban.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Data
@Builder
@ToString
public class ProjectDashboardColumn {
    private String id;
    private String title;
    private ArrayList<String> ticketIds;
}