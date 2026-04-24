package com.project.interfaces;

import com.project.model.ProjectMember;

import java.util.List;

public interface IProjectMemberService {
    ProjectMember addMember(ProjectMember member);
    List<ProjectMember> getMembersByProject(Long projectId);
    List<ProjectMember> getProjectsByUser(Long userId);
    void removeMember(Long id);
    ProjectMember updateRole(Long id, String role);

}
