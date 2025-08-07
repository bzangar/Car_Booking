package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.dto.AuthResponceDto;
import org.example.model.dto.LoginUserDto;
import org.example.model.dto.RegisterUserDto;
import org.example.model.entity.User;
import org.example.model.enums.Role;
import org.example.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponceDto register(RegisterUserDto registerUserDto) {

        if(usernameExist(registerUserDto.getUsername())){
            throw new RuntimeException("Такой пользователь уже есть");
        }

        User user = User.builder()
                .fullName(registerUserDto.getFullName())
                .username(registerUserDto.getUsername())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .role(Role.CLIENT)
                .build();

        userRepository.save(user);
        String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
        );

        return new AuthResponceDto(jwt);
    }

    private boolean usernameExist(String username) {

        return userRepository
                .findByUsername(username)
                .isPresent();
    }

    @Override
    public AuthResponceDto login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword())
        );

        User user = userRepository.findByUsername(loginUserDto.getUsername())
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        String jwt = jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                )
        );

        return new AuthResponceDto(jwt);
    }
}
