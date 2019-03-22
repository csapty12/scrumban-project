package com.scrumban.model.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class SwimLane {
    private int id;
    private String title;
    private String projectIdentifier;
    private List<String> ticketIds;
}
