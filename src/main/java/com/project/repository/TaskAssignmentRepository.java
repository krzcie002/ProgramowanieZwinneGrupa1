package com.project.repository;

import com.project.model.TaskAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskAssignmentRepository extends JpaRepository<TaskAssignment, Long> {

    List<TaskAssignment> findByTaskId(Long taskId);

    List<TaskAssignment> findByUserId(Long userId);

    Optional<TaskAssignment> findByTaskIdAndUserId(Long taskId, Long userId);

    boolean existsByTaskIdAndUserId(Long taskId, Long userId);
}
