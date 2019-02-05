package com.scrumban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateProjectSwimLaneException extends RuntimeException{

    public DuplicateProjectSwimLaneException(String message) {
        super(message);
    }
}
