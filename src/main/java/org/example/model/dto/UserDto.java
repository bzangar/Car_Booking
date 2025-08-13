package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@Schema(description = "Информация о пользователе")
public class UserDto {

    @Schema(description = "ID пользователя", example = "1")
    private Integer id;

    @Schema(description = "Логин пользователя", example = "owner123")
    private String username;

    @Schema(description = "Пароль пользователя (хэш)", example = "$2a$10$hash")
    private String password;

    @Schema(description = "Полное имя", example = "Иван Иванов")
    private String fullName;

    @Schema(description = "Роль пользователя", example = "OWNER")
    private String role;
}
