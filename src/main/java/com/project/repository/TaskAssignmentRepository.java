package com.project.repository;

import com.project.model.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Integer> {

    List<TaskAssignment> findByTaskId(Integer taskId);

    List<TaskAssignment> findByUserId(Integer userId);

    Optional<TaskAssignment> findByTaskIdAndUserId(Integer taskId, Integer userId);

    boolean existsByTaskIdAndUserId(Integer taskId, Integer userId);
}
