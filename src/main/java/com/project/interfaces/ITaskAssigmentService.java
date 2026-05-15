package com.project.interfaces;

import com.project.model.TaskAssignment;

import java.util.List;

public interface ITaskAssigmentService {
    public TaskAssignment assignUser(TaskAssignment assignment);
    public List<TaskAssignment> getUsersByTask(Integer taskId);
    public List<TaskAssignment> getTasksByUser(Integer userId);
    public void removeAssignment(Integer id);
}
