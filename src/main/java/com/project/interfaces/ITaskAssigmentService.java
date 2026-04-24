package com.project.interfaces;

import com.project.model.TaskAssignment;

import java.util.List;

public interface ITaskAssigmentService {
    public TaskAssignment assignUser(TaskAssignment assignment);
    public List<TaskAssignment> getUsersByTask(Long taskId);
    public List<TaskAssignment> getTasksByUser(Long userId);
    public void removeAssignment(Long id);
}
