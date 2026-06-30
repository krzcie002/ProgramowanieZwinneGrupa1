package com.project.service;

import com.project.dto.TaskDto;
import com.project.interfaces.ITaskService;
import com.project.model.Project;
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
        task.setIsDeleted(false);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Integer id, Task updatedTask) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));;

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setStatus(updatedTask.getStatus());

        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
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
    public Optional<Task> getTaskById(Integer id) {
        return taskRepository.findById(id)
                .filter(task -> !task.getIsDeleted());
    }

    @Override
    public List<Task> getTasksByProject(Integer project_id) {
        return taskRepository.findByProject_IdAndIsDeletedFalse(project_id);
    }

    @Override
    public Task changeStatus(Integer id, String status) {
        Task task = getTaskById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        task.setStatus(TaskStatus.valueOf(status));

        return taskRepository.save(task);
    }
    private TaskDto mapTask(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name()
        );
    }

}