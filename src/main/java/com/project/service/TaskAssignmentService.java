package com.project.service;

import com.project.interfaces.ITaskAssigmentService;
import com.project.model.TaskAssignment;
import com.project.repository.TaskAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskAssignmentService implements ITaskAssigmentService {

    private final TaskAssignmentRepository repository;

    public TaskAssignment assignUser(TaskAssignment assignment) {
        Integer taskId = assignment.getTask().getId();
        Integer userId = assignment.getUser().getId();

        if (repository.existsByTaskIdAndUserId(taskId, userId)) {
            throw new RuntimeException("User already assigned to this task");
        }

        return repository.save(assignment);
    }

    public List<TaskAssignment> getUsersByTask(Integer taskId) {
        return repository.findByTaskId(taskId);
    }

    public List<TaskAssignment> getTasksByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    public void removeAssignment(Integer id) {
        repository.deleteById(id);
    }
}