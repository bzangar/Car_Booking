package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.dto.UserResponceDto;

import java.util.List;

public interface UserService {
    List<UserResponceDto> getAllUsers();

    UserDto crateUser(UserDto userDto);

    UserDto getUserById(Integer id);

    boolean deleteUserById(Integer id);

    UserDto updateUserById(Integer id, UserDto userDto);
}
