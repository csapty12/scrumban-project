package com.scrumban.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class SwimLane {

    private String title;
    private List<String> ticketIds;
}
