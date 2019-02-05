package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateProjectSwimLaneResponseException {

    private String name;

    public DuplicateProjectSwimLaneResponseException(String name) {
        this.name = name;
    }
}
