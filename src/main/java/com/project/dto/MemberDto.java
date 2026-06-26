package com.project.dto;

public record MemberDto(
        Integer id,
        String firstName,
        String lastName,
        String role
) {}