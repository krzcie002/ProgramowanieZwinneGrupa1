package com.project.repository;

import com.project.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {

    List<ProjectMember> findByProjectId(Integer projectId);

    List<ProjectMember> findByUserId(Integer userId);

    Optional<ProjectMember> findByUserIdAndProjectId(Integer userId, Integer projectId);

    boolean existsByUserIdAndProjectId(Integer userId, Integer projectId);
}
