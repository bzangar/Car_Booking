package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.UserDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;


    @GetMapping("/{id}")
    public UserDto getUserById(
            @PathVariable Integer id
    ){

        return userService.getUserDtoById(id);
    }

    @GetMapping()
    public List<UserDto> getAllUser(){

        return userService.getAllUser();
    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(
            @PathVariable Integer id
    ){

        return userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    public UserDto updateUserById(
            @PathVariable Integer id, @RequestBody UserDto userDto
    ){

        return userService.updateUserById(id, userDto);
    }
}
