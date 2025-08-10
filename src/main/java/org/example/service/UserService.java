package org.example.service;

import org.example.model.dto.LoginUserDto;
import org.example.model.dto.RegisterUserDto;
import org.example.model.dto.UserDto;
import org.example.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    User getUserById(Integer id);

    User getUserByUsername(String username);

    RegisterUserDto getCurrentUser(UserDetails userDetails);
    RegisterUserDto updateCurrentUser(UserDetails userDetails, RegisterUserDto registerUserDto);
    RegisterUserDto deleteCurrentUser(UserDetails userDetails);
}
