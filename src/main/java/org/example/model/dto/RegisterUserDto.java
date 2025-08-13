package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Модель данных пользователя для регистрации/обновления")
public class RegisterUserDto {
    @Schema(description = "Полное имя пользователя", example = "Иван Иванов")
    private String fullName;

    @Schema(description = "Уникальное имя пользователя (логин)", example = "ivan_123")
    private String username;

    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;
}
