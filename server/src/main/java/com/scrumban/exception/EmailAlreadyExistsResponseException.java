package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailAlreadyExistsResponseException {
    private String email;

    public EmailAlreadyExistsResponseException(String email) {
        this.email = email;
    }
}
