package com.project.service;

import com.project.model.Task;
import com.project.model.TaskStatus;
import com.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectIdAndIsDeletedFalse(projectId);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .filter(task -> !task.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }

    public Task changeStatus(Long id, String status) {
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.valueOf(status));

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        task.setIsDeleted(true);
        taskRepository.save(task);
    }
}