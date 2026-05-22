package com.project.interfaces;

import com.project.dto.ProjectCreateRequest;
import com.project.dto.ProjectDto;
import com.project.dto.ProjectUpdateRequest;
import com.project.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IProjectService {
    ProjectDto createProject(ProjectCreateRequest request);
    ProjectDto updateProject(Integer id, ProjectUpdateRequest request);
    void deleteProject(Integer id);
    List<ProjectDto> getAllProjects();
    ProjectDto getProjectById(Integer id);
}
