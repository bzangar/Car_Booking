package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.model.Mapper;
import org.example.model.dto.*;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.service.AuthService;
import org.example.service.LogoutService;
import org.example.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    final private AuthService authService;
    final private LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponceDto> register(@RequestBody RegisterUserDto registerUserDto){

        return ResponseEntity.ok(authService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponceDto> login(@RequestBody LoginUserDto loginUserDto){

        return ResponseEntity.ok(authService.login(loginUserDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        logoutService.logout(token);
        return ResponseEntity.ok("Logged out successfully");
    }

}
