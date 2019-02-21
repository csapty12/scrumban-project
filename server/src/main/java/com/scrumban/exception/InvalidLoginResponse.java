package com.scrumban.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        username = "Invalid username";
        password = "Invalid password";
    }
}
