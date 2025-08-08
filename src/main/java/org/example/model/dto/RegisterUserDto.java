package org.example.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserDto {
    private String fullName;
    private String username;
    private String password;
}
