package com.project.dto;

import com.project.model.Role;

public record UserCreateElevatedRequest(
        Integer index,
        String email,
        String password,
        String firstName,
        String lastName,
        Role role
) {}