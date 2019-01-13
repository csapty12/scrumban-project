package com.scrumban.model;

import com.scrumban.model.project.ProjectTicket;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@ToString
public class Tickets {
    private List<Map<String, ProjectTicket>> tickets;
    private List<Map<String, ProjectDashboardColumn>> swimLanes;
    private List<String> swimLaneOrder;

}