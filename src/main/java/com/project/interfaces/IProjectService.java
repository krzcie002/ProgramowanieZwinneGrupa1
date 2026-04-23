package com.project.interfaces;

import com.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    Project createProject(Project project);
    Project updateProject(Long id, Project project);
    void deleteProject(Long projectId);
    List<Project> getAllProjects();
    Optional<Project> getProjectById(Long projectId);
}
