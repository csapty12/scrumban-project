package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectIdentifierResponseException {
    private String projectIdentifier;

    public ProjectIdentifierResponseException(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
