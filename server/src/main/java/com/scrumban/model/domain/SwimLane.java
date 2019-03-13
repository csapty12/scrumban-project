package com.scrumban.model.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@EqualsAndHashCode
public class SwimLane {

    private String title;
    private List<String> ticketIds;
}
