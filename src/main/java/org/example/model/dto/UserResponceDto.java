package org.example.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponceDto {
    private Integer id;
    private String email;
    private String fullName;
    private String role;
}
