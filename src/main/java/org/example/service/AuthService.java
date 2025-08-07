package org.example.service;

import org.example.model.dto.AuthResponceDto;
import org.example.model.dto.LoginUserDto;
import org.example.model.dto.RegisterUserDto;

public interface AuthService {
    AuthResponceDto login(LoginUserDto loginUserDto);

    AuthResponceDto register(RegisterUserDto registerUserDto);
}
