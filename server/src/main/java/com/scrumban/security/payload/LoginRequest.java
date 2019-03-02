package com.scrumban.security.payload;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Required field")
    private String email;
    @NotBlank(message = "Required field")
    private String password;
    
}
