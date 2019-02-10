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
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdentifierException projectIdentifierException){
        ProjectIdentifierResponseException projectIdentifierResponseException = new ProjectIdentifierResponseException(projectIdentifierException.getMessage());
        return new ResponseEntity<>(projectIdentifierResponseException, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException projectNotFoundException){
        ProjectNotFoundResponseException projectNotFoundResponseException = new ProjectNotFoundResponseException(projectNotFoundException.getMessage());
        return new ResponseEntity<>(projectNotFoundResponseException, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleDuplicateProjectSwimLaneException(DuplicateProjectSwimLaneException duplicateProjectSwimLaneException){
        DuplicateProjectSwimLaneResponseException duplicateProjectSwimLaneResponseException = new DuplicateProjectSwimLaneResponseException(duplicateProjectSwimLaneException.getMessage());
        return new ResponseEntity<>(duplicateProjectSwimLaneResponseException, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectSwimLaneNotFoundException(ProjectSwimLaneNotFoundException projectSwimLaneNotFoundException){
        ProjectSwimLaneNotFoundReponseException projectSwimLaneNotFoundReponseException = new ProjectSwimLaneNotFoundReponseException(projectSwimLaneNotFoundException.getMessage());
        return new ResponseEntity<>(projectSwimLaneNotFoundReponseException, HttpStatus.BAD_REQUEST);

    }


}
