package com.project.dto;

import com.project.model.User;

public class UserMapper {

    public static UserDto toDto(User u) {
        return new UserDto(
                u.getId(),
                u.getEmail(),
                u.getFirstName(),
                u.getLastName(),
                u.getRole().name()
        );
    }
}
