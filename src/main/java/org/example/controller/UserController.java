package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.UserDto;
import org.example.model.dto.UserResponceDto;
import org.example.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @PostMapping("/create")
    public UserDto createUser(
            @RequestBody UserDto userDto
    ){
        return userService.crateUser(userDto);
    }

    @GetMapping("/get/{id}")
    public UserDto getUserById(
            @PathVariable Integer id
    ){
        return userService.getUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUserById(
            @PathVariable Integer id
    ){
        return userService.deleteUserById(id);
    }

    @PutMapping("/update/{id}")
    public UserDto updateUserById(
            @PathVariable Integer id, @RequestBody UserDto userDto
    ){
        return userService.updateUserById(id, userDto);
    }

    @GetMapping("/all")
    public List<UserResponceDto> getAllUsers(){
        return userService.getAllUsers();
    }
}
