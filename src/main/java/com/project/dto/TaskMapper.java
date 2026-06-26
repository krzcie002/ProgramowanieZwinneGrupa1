package com.project.dto;

import com.project.model.Task;

public class TaskMapper {

    public static TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name()
        );
    }
}
