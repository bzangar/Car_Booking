package org.example.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String email;
    private String password;
    private String fullName;
    private String role;
}
