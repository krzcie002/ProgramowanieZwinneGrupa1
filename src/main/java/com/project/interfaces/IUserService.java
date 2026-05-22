package com.project.interfaces;

import com.project.dto.UserCreateRequest;
import com.project.dto.UserDto;
import com.project.dto.UserUpdateRequest;
import com.project.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    UserDto createUser(UserCreateRequest request);
    UserDto updateUser(Integer id, UserUpdateRequest request);
    void deleteUser(Integer userId);
    List<UserDto> getAllUsers();
    UserDto getUserById(Integer id);
    UserDto getUserByEmail(String email);

    Optional<User> getUserDetailsByEmail(String email);
}
