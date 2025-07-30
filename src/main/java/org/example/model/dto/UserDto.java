package org.example.model.dto;

import lombok.*;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private String role;
}
