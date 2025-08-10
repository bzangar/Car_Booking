package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.UserController;
import org.example.model.dto.RegisterUserDto;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserService userService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }

    @Test
    @WithMockUser(username = "zangar", roles = "CLIENT")
    void testGetCurrentUser() throws Exception {
        RegisterUserDto dto = RegisterUserDto.builder()
                .fullName("Zangar Bokenov")
                .username("zangar")
                .password("123")
                .build();

        Mockito.when(userService.getCurrentUser(any(UserDetails.class)))
                .thenReturn(dto);

        mockMvc.perform(get("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Zangar Bokenov"))
                .andExpect(jsonPath("$.username").value("zangar"))
                .andExpect(jsonPath("$.password").value("123"));
    }

    @Test
    @WithMockUser(username = "zangar", roles = "CLIENT")
    void testUpdateCurrentUser() throws Exception {
        RegisterUserDto updateDto = RegisterUserDto.builder()
                .fullName("Updated Name")
                .username("zangarUpdated")
                .password("newPass")
                .build();

        Mockito.when(userService.updateCurrentUser(any(UserDetails.class), any(RegisterUserDto.class)))
                .thenReturn(updateDto);

        mockMvc.perform(put("/api/users/me")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Updated Name"))
                .andExpect(jsonPath("$.username").value("zangarUpdated"))
                .andExpect(jsonPath("$.password").value("newPass"));
    }

    @Test
    @WithMockUser(username = "zangar", roles = "CLIENT")
    void testDeleteCurrentUser() throws Exception {
        RegisterUserDto dto = RegisterUserDto.builder()
                .fullName("Deleted User")
                .username("zangar")
                .password("123")
                .build();

        Mockito.when(userService.deleteCurrentUser(any(UserDetails.class)))
                .thenReturn(dto);

        mockMvc.perform(delete("/api/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Deleted User"))
                .andExpect(jsonPath("$.username").value("zangar"))
                .andExpect(jsonPath("$.password").value("123"));
    }
}
