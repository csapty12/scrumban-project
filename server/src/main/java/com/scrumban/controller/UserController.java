package com.scrumban.controller;

import com.scrumban.model.domain.User;
import com.scrumban.service.user.UserService;
import com.scrumban.service.ValidationErrorService;
import com.scrumban.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private ValidationErrorService validationErrorService;
    private UserService userService;
    private UserValidator userValidator;

    public UserController(ValidationErrorService validationErrorService, UserService userService, UserValidator userValidator) {
        this.validationErrorService = validationErrorService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){
        //validate passwords match
        userValidator.validate(user,bindingResult);
        ResponseEntity<?> errorMap = validationErrorService.validateObject(bindingResult);
        if(errorMap!=null){
            return errorMap;
        }
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
