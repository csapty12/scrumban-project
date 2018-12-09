package com.scrumban.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException projectIdException){
        ProjectIdResponseException projectIdResponseException = new ProjectIdResponseException(projectIdException.getMessage());
        return new ResponseEntity<>(projectIdResponseException, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException projectNotFoundException){
        ProjectNotFoundResponseException projectNotFoundResponseException = new ProjectNotFoundResponseException(projectNotFoundException.getMessage());
        return new ResponseEntity<>(projectNotFoundResponseException, HttpStatus.BAD_REQUEST);

    }


}
