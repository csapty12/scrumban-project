package com.scrumban.model.domain;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@ToString
@EqualsAndHashCode
public class ProjectDashboard {
    private List<LinkedHashMap<String, ProjectTicket>> tickets;
    private List<Map<String, SwimLane>> swimLanes;
    private List<String> swimLaneOrder;

}