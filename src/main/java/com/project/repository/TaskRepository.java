package com.project.repository;

import com.project.model.ProjectMember;
import com.project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findByProjectIdAndIsDeletedFalse(Integer projectId);

    Optional<Task> findByIdAndIsDeletedFalse(Integer id);
    List<Task> findByProject_IdAndIsDeletedFalse(Integer projectId);

    List<Task> findByProject_Id(Integer projectId);
    List<Task> findByProjectId(Integer projectId);

    List<Task> findByCreatedById(Integer userId);
}
