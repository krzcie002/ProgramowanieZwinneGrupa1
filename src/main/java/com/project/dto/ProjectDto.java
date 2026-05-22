package com.project.dto;

import java.time.LocalDateTime;

public record ProjectDto(
        Integer id,
        String name,
        String description,
        String status,
        Boolean isDeleted,
        LocalDateTime createdAt,
        Integer ownerId
) {}
