package org.example.model.dto;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String fullName;
    private String username;
    private String password;
}
