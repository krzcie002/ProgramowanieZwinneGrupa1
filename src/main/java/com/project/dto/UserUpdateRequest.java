package com.project.dto;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        Boolean isActive
) {}
