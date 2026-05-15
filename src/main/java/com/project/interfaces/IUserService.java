package com.project.interfaces;

import com.project.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User createUser(User user);
    User updateUser(Integer id,User user);
    void deleteUser(Integer userId);
    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    Optional<User> getUserByEmail(String email);
}
