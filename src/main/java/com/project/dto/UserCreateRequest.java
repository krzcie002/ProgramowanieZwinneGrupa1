package com.project.dto;

public record UserCreateRequest(
        String email,
        String password,
        String firstName,
        String lastName
) {}