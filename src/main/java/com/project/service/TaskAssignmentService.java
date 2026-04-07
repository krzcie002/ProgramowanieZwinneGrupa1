package com.project.service;

import com.project.model.TaskAssignment;
import com.project.repository.TaskAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService {

    private final TaskAssignmentRepository repository;

    public TaskAssignment assignUser(TaskAssignment assignment) {
        Long taskId = assignment.getTask().getId();
        Long userId = assignment.getUser().getId();

        if (repository.existsByTaskIdAndUserId(taskId, userId)) {
            throw new RuntimeException("User already assigned to this task");
        }

        return repository.save(assignment);
    }

    public List<TaskAssignment> getUsersByTask(Long taskId) {
        return repository.findByTaskId(taskId);
    }

    public List<TaskAssignment> getTasksByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public void removeAssignment(Long id) {
        repository.deleteById(id);
    }
}