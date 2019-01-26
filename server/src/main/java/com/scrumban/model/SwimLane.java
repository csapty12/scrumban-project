package com.scrumban.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SwimLane {

    private String title;
    private List<String> ticketIds;
}
