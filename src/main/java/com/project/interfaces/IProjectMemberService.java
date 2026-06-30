package com.project.interfaces;

import com.project.model.ProjectMember;

import java.util.List;

public interface IProjectMemberService {
    ProjectMember addMember(ProjectMember member);

    ProjectMember addMember(Integer projectId, Integer userId);

    List<ProjectMember> getMembersByProject(Integer projectId);
    List<ProjectMember> getProjectsByUser(Integer userId);
    void removeMember(Integer id);
    ProjectMember updateRole(Integer id, String role);

}
