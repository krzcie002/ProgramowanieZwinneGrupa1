package com.project.dto;

public record UserCreateRequest(
        Integer index,
        String email,
        String password,
        String firstName,
        String lastName
) {}