package org.example.model.dto;

import lombok.*;


@Data
@Builder
public class UserResponceDto {
    private Integer id;
    private String username;
    private String fullName;
    private String role;
}
