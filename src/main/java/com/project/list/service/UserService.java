package com.project.list.service;

import com.project.list.model.User;
import com.project.list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getIsDeleted())
                .toList();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .filter(user -> !user.getIsDeleted())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setRole(updatedUser.getRole());
        user.setIsActive(updatedUser.getIsActive());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        user.setIsDeleted(true); // soft delete
        userRepository.save(user);
    }
}
