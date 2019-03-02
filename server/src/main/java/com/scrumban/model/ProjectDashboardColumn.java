package com.scrumban.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;


@Data
@Builder
@ToString
public class ProjectDashboardColumn {
    private String title;
    private ArrayList<String> ticketIds;
}