package com.scrumban.controller;

import com.scrumban.model.domain.User;
import com.scrumban.security.JwtTokenProvider;
import com.scrumban.security.payload.JWTLoginResponse;
import com.scrumban.security.payload.LoginRequest;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {


    private ValidationErrorService validationErrorService;
    private UserService userService;

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    public UserController(ValidationErrorService validationErrorService, UserService userService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.validationErrorService = validationErrorService;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        userService.validateUserDetails(user, bindingResult);

        ResponseEntity<?> errorMap = validateRequest(bindingResult);
        if (errorMap != null) return errorMap;

        User newUser = userService.saveNewUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {

        ResponseEntity<?> errorMap = validateRequest(bindingResult);
        if (errorMap != null) return errorMap;

        log.info("attempting to authenticate user");
        String jwt = jwtTokenProvider.getJwtForAuthenticatedUser(loginRequest, authenticationManager);
        return ResponseEntity.ok(new JWTLoginResponse(true, jwt));

    }

    private ResponseEntity<?> validateRequest(BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            return errorMap;
        }
        return null;
    }
}
