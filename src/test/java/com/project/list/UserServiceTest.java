package com.project.list;

import com.project.model.Role;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.service.UserService;
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
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setIsDeleted(false);
        user.setIsActive(true);
    }

    // -------- createUser --------

    @Test
    void shouldCreateUser_whenEmailDoesNotExist() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowException_whenEmailExists() {
        when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.createUser(user));

        assertEquals("Email already exists", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    // -------- getAllUsers --------

    @Test
    void shouldReturnOnlyNotDeletedUsers() {
        User deletedUser = new User();
        deletedUser.setIsDeleted(true);

        when(userRepository.findAll()).thenReturn(List.of(user, deletedUser));

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertFalse(result.get(0).getIsDeleted());
    }

    // -------- getUserById --------

    @Test
    void shouldReturnUser_whenExistsAndNotDeleted() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertEquals(user.getId(), result.get().getId());
    }

    @Test
    void shouldThrowException_whenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> userService.getUserById(1L));
    }

    @Test
    void shouldThrowException_whenUserIsDeleted() {
        user.setIsDeleted(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class,
                () -> userService.getUserById(1L));
    }

    // -------- updateUser --------

    @Test
    void shouldUpdateUser() {
        User updated = new User();
        updated.setFirstName("Anna");
        updated.setLastName("Nowak");
        updated.setRole(Role.valueOf("student"));
        updated.setIsActive(false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, updated);

        assertEquals("Anna", result.getFirstName());
        assertEquals("Nowak", result.getLastName());
        assertEquals(Role.valueOf("student"), result.getRole());
        assertFalse(result.getIsActive());

        verify(userRepository).save(user);
    }

    // -------- deleteUser --------

    @Test
    void shouldSoftDeleteUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.deleteUser(1L);

        assertTrue(user.getIsDeleted());
        verify(userRepository).save(user);
    }
}