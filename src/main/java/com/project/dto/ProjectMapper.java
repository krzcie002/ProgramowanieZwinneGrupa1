package com.project.dto;

import com.project.model.Project;

public class ProjectMapper {

    public static ProjectDto toDto(Project p) {
        return new ProjectDto(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getStatus(),
                p.getIsDeleted(),
                p.getCreatedAt(),
                p.getOwner() != null ? p.getOwner().getId() : null
        );
    }
}