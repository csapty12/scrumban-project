package com.scrumban.controller;

import com.scrumban.model.domain.User;
import com.scrumban.security.JwtTokenProvider;
import com.scrumban.security.payload.JWTLoginResponse;
import com.scrumban.security.payload.LoginRequest;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.service.user.UserService;
import com.scrumban.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    private ValidationErrorService validationErrorService;
    private UserService userService;
    private UserValidator userValidator;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    public UserController(ValidationErrorService validationErrorService, UserService userService, UserValidator userValidator, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.validationErrorService = validationErrorService;
        this.userService = userService;
        this.userValidator = userValidator;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        //validate passwords match
        userValidator.validate(user, bindingResult);
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            return errorMap;
        }
        User newUser = userService.saveUser(user);
        log.info("new user: " + newUser.getUsername() + "created");
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if (errorMap != null) {
            return errorMap;
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenPrefix +jwtTokenProvider.generateToken(authentication);
        log.info("login has been attempted");
        return ResponseEntity.ok(new JWTLoginResponse(true, jwt));

    }
}
