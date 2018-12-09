package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectNotFoundResponseException {
    private String projectIdentifier;

    public ProjectNotFoundResponseException(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
