package com.project.dto;

public record UserDto(
        Integer id,
        String email,
        String firstName,
        String lastName,
        String role
) {}
