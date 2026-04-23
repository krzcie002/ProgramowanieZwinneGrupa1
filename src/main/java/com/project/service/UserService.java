package com.project.service;

import com.project.interfaces.IUserService;
import com.project.model.User;
import com.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setRole(updatedUser.getRole());
        user.setIsActive(updatedUser.getIsActive());

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setIsDeleted(true); // soft delete
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getIsDeleted())
                .toList();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id)
                .filter(user -> !user.getIsDeleted());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(user -> !user.getIsDeleted());
    }
}
