package com.project.dto;

public record ProjectCreateRequest(
        String name,
        String description,
        String status,
        Integer ownerId
) {}
