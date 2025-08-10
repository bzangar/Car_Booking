package org.example;

import org.example.exception.UserNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.RegisterUserDto;
import org.example.model.entity.User;
import org.example.model.enums.Role;
import org.example.repository.UserRepository;
import org.example.service.JwtService;
import org.example.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = User.builder()
                .id(1)
                .username("zangar")
                .password("123")
                .fullName("Zangar Bokenov")
                .role(Role.CLIENT)
                .build();
    }

    @Test
    void getUserById_found() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1);

        assertEquals("zangar", result.getUsername());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1));
    }

    @Test
    void getUserByUsername_found() {
        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(testUser));

        User result = userService.getUserByUsername("zangar");

        assertEquals("Zangar Bokenov", result.getFullName());
        verify(userRepository).findByUsername("zangar");
    }

    @Test
    void getUserByUsername_notFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername("unknown"));
    }

    @Test
    void loadUserByUsername_found() {
        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = userService.loadUserByUsername("zangar");

        assertEquals("zangar", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENT")));
    }

    @Test
    void loadUserByUsername_notFound() {
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.loadUserByUsername("unknown"));
    }

    @Test
    void getCurrentUser_returnsDto() {
        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(testUser));

        UserDetails principal = org.springframework.security.core.userdetails.User
                .withUsername("zangar").password("123").roles("USER").build();

        RegisterUserDto dto = userService.getCurrentUser(principal);

        assertEquals("zangar", dto.getUsername());
        assertEquals("Zangar Bokenov", dto.getFullName());
    }

    @Test
    void updateCurrentUser_updatesFields() {
        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserDetails principal = org.springframework.security.core.userdetails.User
                .withUsername("zangar").password("123").roles("USER").build();

        RegisterUserDto updateDto = RegisterUserDto.builder()
                .username("newUser")
                .password("456")
                .fullName("New Name")
                .build();

        RegisterUserDto result = userService.updateCurrentUser(principal, updateDto);

        assertEquals("newUser", result.getUsername());
        assertEquals("456", result.getPassword());
        assertEquals("New Name", result.getFullName());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteCurrentUser_deletesFromRepo() {
        when(userRepository.findByUsername("zangar")).thenReturn(Optional.of(testUser));

        UserDetails principal = org.springframework.security.core.userdetails.User
                .withUsername("zangar").password("123").roles("USER").build();

        RegisterUserDto result = userService.deleteCurrentUser(principal);

        assertEquals("zangar", result.getUsername());
        verify(userRepository).delete(testUser);
    }
}
