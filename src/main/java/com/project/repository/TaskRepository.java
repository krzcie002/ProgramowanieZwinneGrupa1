package com.project.repository;

import com.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProjectIdAndIsDeletedFalse(Integer projectId);

    List<Task> findByCreatedById(Integer userId);
}
