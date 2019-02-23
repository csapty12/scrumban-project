package com.scrumban.security.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class JWTLoginResponse {
    private boolean success;
    private String token;

}
