package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.LoginUserDto;
import org.example.model.dto.RegisterUserDto;
import org.example.model.dto.UserDto;
import org.example.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
@Tag(name = "User", description = "Операции с текущим пользователем (личный профиль)")
public class UserController {

    final private UserService userService;

    @Operation(
            summary = "Получить информацию о текущем пользователе",
            description = "Возвращает данные авторизованного пользователя, включая имя, логин и пароль (хэш).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешно получено",
                            content = @Content(schema = @Schema(implementation = RegisterUserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Неавторизован")
            }
    )
    @GetMapping()
    public RegisterUserDto getCurrentUser(@Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails){

        return userService.getCurrentUser(userDetails);
    }

    @Operation(
            summary = "Обновить данные текущего пользователя",
            description = "Позволяет изменить имя, логин и/или пароль текущего авторизованного пользователя.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Новые данные пользователя",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterUserDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "fullName": "Иван Иванов",
                                      "username": "ivan_123",
                                      "password": "new_password"
                                    }
                                    """)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные успешно обновлены",
                            content = @Content(schema = @Schema(implementation = RegisterUserDto.class))),
                    @ApiResponse(responseCode = "400", description = "Неверные данные"),
                    @ApiResponse(responseCode = "401", description = "Неавторизован")
            }
    )
    @PutMapping()
    public RegisterUserDto updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RegisterUserDto registerUserDto){

        return userService.updateCurrentUser(userDetails, registerUserDto);
    }


    @Operation(
            summary = "Удалить текущего пользователя",
            description = "Удаляет аккаунт текущего авторизованного пользователя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Аккаунт успешно удалён",
                            content = @Content(schema = @Schema(implementation = RegisterUserDto.class))),
                    @ApiResponse(responseCode = "401", description = "Неавторизован")
            }
    )
    @DeleteMapping()
    public RegisterUserDto deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails){

        return userService.deleteCurrentUser(userDetails);
    }
}
