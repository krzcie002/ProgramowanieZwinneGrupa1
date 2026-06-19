package com.project.dto;

import com.project.model.Role;

public record UserDto(
        Integer id,
        String email,
        String firstName,
        String lastName,
        Role role
) {}
