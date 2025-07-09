package org.example.service;

import org.example.model.dto.UserDto;
import org.example.model.entity.User;


public interface UserService {

    UserDto crateUser(UserDto userDto);

    UserDto getUserDtoById(Integer id);

    boolean deleteUserById(Integer id);

    UserDto updateUserById(Integer id, UserDto userDto);

    User getUserById(Integer id);
}
