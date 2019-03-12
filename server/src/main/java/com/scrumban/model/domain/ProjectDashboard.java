package com.scrumban.model.domain;

import com.scrumban.model.domain.ProjectDashboardSwimLane;
import com.scrumban.model.project.entity.ProjectTicket;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@ToString
public class ProjectDashboard {
    private List<LinkedHashMap<String, ProjectTicket>> tickets;
    private List<Map<String, ProjectDashboardSwimLane>> swimLanes;
    private List<String> swimLaneOrder;

}