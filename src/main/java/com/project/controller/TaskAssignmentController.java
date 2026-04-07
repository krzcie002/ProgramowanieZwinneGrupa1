package com.project.controller;

import com.project.model.TaskAssignment;
import com.project.service.TaskAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task-assignments")
@RequiredArgsConstructor
public class TaskAssignmentController {

    private final TaskAssignmentService service;

    @PostMapping
    public TaskAssignment assignUser(@RequestBody TaskAssignment assignment) {
        return service.assignUser(assignment);
    }

    @GetMapping("/task/{taskId}")
    public List<TaskAssignment> getUsersByTask(@PathVariable Long taskId) {
        return service.getUsersByTask(taskId);
    }

    @GetMapping("/user/{userId}")
    public List<TaskAssignment> getTasksByUser(@PathVariable Long userId) {
        return service.getTasksByUser(userId);
    }

    @DeleteMapping("/{id}")
    public void removeAssignment(@PathVariable Long id) {
        service.removeAssignment(id);
    }
}