package com.project.dto;

import com.project.model.ProjectMember;

public class ProjectMemberMapper {

    public static MemberDto toDto(ProjectMember member) {
        return new MemberDto(
                member.getUser().getId(),
                member.getUser().getFirstName(),
                member.getUser().getLastName(),
                member.getRole()
        );
    }
}