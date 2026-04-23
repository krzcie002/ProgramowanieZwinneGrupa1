package com.project.service;

import com.project.interfaces.IProjectService;
import com.project.model.Project;
import com.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService implements IProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Long id, Project updatedProject) {
        Project project = getProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setStatus(updatedProject.getStatus());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = getProjectById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findByIsDeletedFalse();
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id)
                .filter(p -> !p.getIsDeleted());
    }




}
