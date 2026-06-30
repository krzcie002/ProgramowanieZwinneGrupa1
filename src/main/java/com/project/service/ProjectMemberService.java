package com.project.service;

import com.project.dto.MemberDto;
import com.project.interfaces.IProjectMemberService;
import com.project.model.Project;
import com.project.model.ProjectMember;
import com.project.model.User;
import com.project.repository.ProjectMemberRepository;
import com.project.repository.ProjectRepository;
import com.project.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectMemberService implements IProjectMemberService {

    private final ProjectMemberRepository repository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectMember addMember(ProjectMember member) {
        if (repository.existsByUserIdAndProjectId(
                member.getUser().getId(),
                member.getProject().getId())) {
            throw new RuntimeException("User already in project");
        }

        return repository.save(member);
    }
    @Override
    public ProjectMember addMember(Integer projectId, Integer userId) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (repository.existsByUserIdAndProjectId(userId, projectId)) {
            throw new RuntimeException("Użytkownik jest już przypisany do projektu.");
        }

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(user)
                .role("student")
                .build();

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
    @Transactional
    public void removeMember(Integer projectId, Integer userId) {
        repository.deleteByUserIdAndProjectId(userId, projectId);
    }

    public ProjectMember updateRole(Integer id, String role) {
        ProjectMember member = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        member.setRole(role);
        return repository.save(member);
    }
    MemberDto mapMember(ProjectMember member) {
        return new MemberDto(
                member.getUser().getId(),
                member.getUser().getFirstName(),
                member.getUser().getLastName(),
                member.getRole()
        );
    }
}
