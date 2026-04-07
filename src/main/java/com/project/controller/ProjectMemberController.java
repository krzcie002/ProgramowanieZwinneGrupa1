package com.project.controller;

import com.project.model.ProjectMember;
import com.project.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService service;

    @PostMapping
    public ProjectMember addMember(@RequestBody ProjectMember member) {
        return service.addMember(member);
    }

    @GetMapping("/project/{projectId}")
    public List<ProjectMember> getMembersByProject(@PathVariable Long projectId) {
        return service.getMembersByProject(projectId);
    }

    @GetMapping("/user/{userId}")
    public List<ProjectMember> getProjectsByUser(@PathVariable Long userId) {
        return service.getProjectsByUser(userId);
    }

    @PutMapping("/{id}/role")
    public ProjectMember updateRole(@PathVariable Long id, @RequestParam String role) {
        return service.updateRole(id, role);
    }

    @DeleteMapping("/{id}")
    public void removeMember(@PathVariable Long id) {
        service.removeMember(id);
    }
}
