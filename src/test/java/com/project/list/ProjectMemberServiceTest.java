package com.project.list;

import com.project.model.Project;
import com.project.model.ProjectMember;
import com.project.model.User;
import com.project.repository.ProjectMemberRepository;
import com.project.service.ProjectMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectMemberServiceTest {

    @Mock
    private ProjectMemberRepository repository;

    @InjectMocks
    private ProjectMemberService service;

    private ProjectMember member;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1);

        Project project = new Project();
        project.setId(10);

        member = new ProjectMember();
        member.setId(100);
        member.setUser(user);
        member.setProject(project);
        member.setRole("USER");
    }

    @Test
    void shouldAddMember() {
        when(repository.existsByUserIdAndProjectId(1, 10)).thenReturn(false);
        when(repository.save(member)).thenReturn(member);

        ProjectMember result = service.addMember(member);

        assertNotNull(result);
        assertEquals("USER", result.getRole());

        verify(repository).existsByUserIdAndProjectId(1, 10);
        verify(repository).save(member);
    }

    @Test
    void shouldThrowExceptionWhenMemberAlreadyExists() {
        when(repository.existsByUserIdAndProjectId(1, 10)).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.addMember(member)
        );

        assertEquals("User already in project", exception.getMessage());

        verify(repository).existsByUserIdAndProjectId(1, 10);
        verify(repository, never()).save(any());
    }

    @Test
    void shouldReturnMembersByProject() {
        when(repository.findByProjectId(10))
                .thenReturn(List.of(member));

        List<ProjectMember> result = service.getMembersByProject(10);

        assertEquals(1, result.size());

        verify(repository).findByProjectId(10);
    }

    @Test
    void shouldReturnProjectsByUser() {
        when(repository.findByUserId(1))
                .thenReturn(List.of(member));

        List<ProjectMember> result = service.getProjectsByUser(1);

        assertEquals(1, result.size());

        verify(repository).findByUserId(1);
    }

    @Test
    void shouldRemoveMember() {
        service.removeMember(100);

        verify(repository).deleteById(100);
    }

    @Test
    void shouldUpdateRole() {
        when(repository.findById(100))
                .thenReturn(Optional.of(member));

        when(repository.save(any(ProjectMember.class)))
                .thenReturn(member);

        ProjectMember result = service.updateRole(100, "ADMIN");

        assertEquals("ADMIN", result.getRole());

        verify(repository).findById(100);
        verify(repository).save(member);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingMember() {
        when(repository.findById(100))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> service.updateRole(100, "ADMIN")
        );

        assertEquals("Member not found", exception.getMessage());

        verify(repository).findById(100);
        verify(repository, never()).save(any());
    }
}