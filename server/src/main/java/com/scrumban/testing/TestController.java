package com.scrumban.testing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;

@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<?> testParse(@Valid @RequestBody Tasks tasks, BindingResult bindingResult) {

        Tasks newTask = new Tasks();
        newTask.setTasks(getTask());
        System.out.println("tasks " + tasks.toString());
        return new ResponseEntity<>(newTask, HttpStatus.OK);
    }

    private List<Map<String,Task>> getTask() {
        Map<String, Task> map = new HashMap<>();
        Task task = getSingleTask();
        map.put(task.getId() ,task);
        return  new ArrayList<>(Arrays.asList(map,map,map));
    }

    private Task getSingleTask() {
        Task task = new Task();
        task.setId("task-1");
        task.setContent("Take out the garbage");
        return task;
    }



}
