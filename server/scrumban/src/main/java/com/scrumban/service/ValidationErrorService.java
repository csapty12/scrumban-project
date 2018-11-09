package com.scrumban.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

@Service
public class ValidationErrorService {

    public ResponseEntity<?> validateObject(BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            //check if the object contains ay invalid content. if it does, add the errors to a map and return the map
            Map<String, String> mapOfErrors=new HashMap<>();
            bindingResult.getFieldErrors().forEach(item-> mapOfErrors.put(item.getField(), item.getDefaultMessage()));

            return new ResponseEntity<>(mapOfErrors, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
