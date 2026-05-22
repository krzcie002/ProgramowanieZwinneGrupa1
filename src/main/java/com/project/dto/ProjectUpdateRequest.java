package com.project.dto;

public record ProjectUpdateRequest (
        String name,
        String description,
        String status
) {}
