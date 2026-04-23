package com.project.interfaces;

import com.project.model.User;
import com.project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user);
    User updateUser(Long id,User user);
    void deleteUser(Long userId);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    Optional<User> getUserByEmail(String email);
}
