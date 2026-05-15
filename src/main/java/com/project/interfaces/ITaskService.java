package com.project.interfaces;

import com.project.model.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskService {
    Task createTask(Task task);
    Task updateTask(Integer id, Task task);
    void deleteTask(Integer taskId);
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Integer taskId);
    List<Task> getTasksByProject(Integer projectId);
    Task changeStatus(Integer id, String status);
}

