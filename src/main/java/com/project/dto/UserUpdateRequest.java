package com.project.dto;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String role,
        Boolean isActive
) {}
