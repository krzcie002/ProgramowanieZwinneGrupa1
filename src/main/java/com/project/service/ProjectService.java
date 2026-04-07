package com.project.service;

import com.project.model.Project;
import com.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findByIsDeletedFalse();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .filter(p -> !p.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project project = getProjectById(id);

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setStatus(updatedProject.getStatus());

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = getProjectById(id);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }
}
