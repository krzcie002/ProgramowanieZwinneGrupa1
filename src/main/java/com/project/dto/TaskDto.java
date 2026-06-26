package com.project.dto;

public record TaskDto(
        Integer id,
        String title,
        String description,
        String status
) {}