package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;

import java.util.List;


public interface UserService {

    UserDto crateUser(UserDto userDto);

    UserDto getUserDtoById(Integer id);

    List<UserDto> getAllUser();

    boolean deleteUserById(Integer id);

    UserDto updateUserById(Integer id, UserDto userDto);

    User getUserById(Integer id);

    boolean existsByUsername(String username);

    User getUserByUsername(String username);
}
