package com.project.controller;

import com.project.dto.ProjectCreateRequest;
import com.project.dto.ProjectDto;
import com.project.dto.ProjectUpdateRequest;
import com.project.model.Project;
import com.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ProjectDto createProject(@RequestBody ProjectCreateRequest request) {
        return projectService.createProject(request);
    }

    @PutMapping("/{id}")
    public ProjectDto updateProject(
            @PathVariable Integer id,
            @RequestBody ProjectUpdateRequest request
    ) {
        return projectService.updateProject(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }

    @GetMapping
    public List<ProjectDto> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ProjectDto getProjectById(@PathVariable Integer id) {
        return projectService.getProjectById(id);
    }
}
