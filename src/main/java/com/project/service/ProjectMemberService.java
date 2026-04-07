package com.project.service;

import com.project.model.ProjectMember;
import com.project.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository repository;

    public ProjectMember addMember(ProjectMember member) {
        if (repository.existsByUserIdAndProjectId(
                member.getUser().getId(),
                member.getProject().getId())) {
            throw new RuntimeException("User already in project");
        }

        return repository.save(member);
    }

    public List<ProjectMember> getMembersByProject(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public List<ProjectMember> getProjectsByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    public void removeMember(Long id) {
        repository.deleteById(id);
    }

    public ProjectMember updateRole(Long id, String role) {
        ProjectMember member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setRole(role);
        return repository.save(member);
    }
}
