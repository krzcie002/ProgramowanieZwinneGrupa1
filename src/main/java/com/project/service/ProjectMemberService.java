package com.project.service;

import com.project.interfaces.IProjectMemberService;
import com.project.model.ProjectMember;
import com.project.repository.ProjectMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService implements IProjectMemberService {

    private final ProjectMemberRepository repository;

    public ProjectMember addMember(ProjectMember member) {
        if (repository.existsByUserIdAndProjectId(
                member.getUser().getId(),
                member.getProject().getId())) {
            throw new RuntimeException("User already in project");
        }

        return repository.save(member);
    }

    public List<ProjectMember> getMembersByProject(Integer projectId) {
        return repository.findByProjectId(projectId);
    }

    public List<ProjectMember> getProjectsByUser(Integer userId) {
        return repository.findByUserId(userId);
    }

    public void removeMember(Integer id) {
        repository.deleteById(id);
    }

    public ProjectMember updateRole(Integer id, String role) {
        ProjectMember member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setRole(role);
        return repository.save(member);
    }
}
