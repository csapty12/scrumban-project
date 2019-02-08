package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundResponseException {
    private String project;

    public ProjectNotFoundResponseException(String projectIdentifier) {
        this.project = projectIdentifier;
    }
}
