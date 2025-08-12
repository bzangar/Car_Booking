package org.example;

import org.example.exception.UserNotFoundException;
import org.example.model.dto.AuthResponceDto;
import org.example.model.dto.LoginUserDto;
import org.example.model.dto.RegisterUserDto;
import org.example.model.entity.User;
import org.example.model.enums.Role;
import org.example.repository.UserRepository;
import org.example.service.AuthServiceImpl;
import org.example.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_Success() {
        RegisterUserDto dto = RegisterUserDto.builder()
                .fullName("Zangar Bokenov")
                .username("zangar")
                .password("pass")
                .build();

        when(userRepository.findByUsername("zangar")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("pass")).thenReturn("encodedPass");

        User savedUser = User.builder()
                .username("zangar")
                .password("encodedPass")
                .role(Role.CLIENT)
                .build();

        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        AuthResponceDto response = authService.register(dto);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_UserAlreadyExists_ThrowsException() {
        RegisterUserDto dto = RegisterUserDto.builder()
                .username("zangar")
                .build();

        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> authService.register(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_Success() {
        LoginUserDto dto = LoginUserDto.builder()
                .username("zangar")
                .password("pass")
                .build();

        User existingUser = User.builder()
                .username("zangar")
                .password("encodedPass")
                .role(Role.CLIENT)
                .build();

        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(existingUser));
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("jwt-token");

        AuthResponceDto response = authService.login(dto);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(authenticationManager).authenticate(
                new UsernamePasswordAuthenticationToken("zangar", "pass")
        );
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        LoginUserDto dto = LoginUserDto.builder()
                .username("zangar")
                .password("pass")
                .build();

        when(userRepository.findByUsername("zangar")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.login(dto));
    }
}
