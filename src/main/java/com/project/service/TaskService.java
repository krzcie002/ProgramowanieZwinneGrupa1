package com.project.service;

import com.project.interfaces.ITaskService;
import com.project.model.Task;
import com.project.model.TaskStatus;
import com.project.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService implements ITaskService {

    private final TaskRepository taskRepository;

    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));;

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));;
        task.setIsDeleted(true);
        taskRepository.save(task);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .filter(task -> !task.getIsDeleted())
                .toList();
    }

    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id)
                .filter(task -> !task.getIsDeleted());
    }

    @Override
    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectIdAndIsDeletedFalse(projectId);
    }

    @Override
    public Task changeStatus(Long id, String status) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        task.setStatus(TaskStatus.valueOf(status));

        return taskRepository.save(task);
    }
}