package com.project.interfaces;

import com.project.model.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    Task createTask(Task task);
    Task updateTask(Long id, Task task);
    void deleteTask(Long taskId);
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long taskId);
    List<Task> getTasksByProject(Long projectId);
    Task changeStatus(Long id, String status);
}

