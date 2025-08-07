package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.model.Mapper;
import org.example.model.dto.*;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.service.AuthService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    final private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponceDto> register(@RequestBody RegisterUserDto registerUserDto){

        return ResponseEntity.ok(authService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponceDto> login(@RequestBody LoginUserDto loginUserDto){

        return ResponseEntity.ok(authService.login(loginUserDto));
    }
}
