package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectSwimLaneNotFoundReponseException {
    private String name;

    public ProjectSwimLaneNotFoundReponseException(String name) {
        this.name = name;
    }
}