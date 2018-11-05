package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectIdResponseException{
    private String projectIdentifier;

    public ProjectIdResponseException( String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }
}
