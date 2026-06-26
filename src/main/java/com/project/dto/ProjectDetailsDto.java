package com.project.dto;

import java.util.List;

public record ProjectDetailsDto(
        Integer id,
        String name,
        String description,
        String status,
        OwnerDto owner,
        List<MemberDto> members,
        List<TaskDto> tasks
) {}
