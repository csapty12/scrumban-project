package com.scrumban.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin
@Slf4j
public class ProjectDashboardController {

    @GetMapping("/{projectIdentifier}")
    public ResponseEntity<?> getAllTickets(@PathVariable String projectIdentifier){

        System.out.println("inside get all tickets: " + projectIdentifier);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
