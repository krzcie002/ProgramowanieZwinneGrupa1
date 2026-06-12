package com.project.service;

import com.project.dto.ProjectCreateRequest;
import com.project.dto.ProjectDto;
import com.project.dto.ProjectMapper;
import com.project.dto.ProjectUpdateRequest;
import com.project.interfaces.IProjectService;
import com.project.model.Project;
import com.project.model.User;
import com.project.repository.ProjectRepository;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Override
    public ProjectDto createProject(ProjectCreateRequest request) {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = Project.builder()
                .name(request.name())
                .description(request.description())
                .owner(owner)
                .status("active")
                .isDeleted(false)
                .build();

        return ProjectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public ProjectDto updateProject(Integer id, ProjectUpdateRequest request) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(request.name());
        project.setDescription(request.description());
        project.setStatus(request.status());

        return ProjectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public List<ProjectDto> getAllProjects() {
        return projectRepository.findByIsDeletedFalse()
                .stream()
                .map(ProjectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectDto getProjectById(Integer id) {
        return projectRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .map(ProjectMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}
