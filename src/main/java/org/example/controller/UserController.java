package org.example.controller;

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
public class UserController {

    final private UserService userService;


    @GetMapping("/{id}")
    public UserDto getUserById(
            @PathVariable Integer id
    ){

        return userService.getUserDtoById(id);
    }

//    @GetMapping()
//    public List<UserDto> getAllUser(){
//
//        return userService.getAllUser();
//    }

    @DeleteMapping("/{id}")
    public boolean deleteUserById(
            @PathVariable Integer id
    ){

        return userService.deleteUserById(id);
    }

//    @PutMapping("/{id}")
//    public UserDto updateUserById(
//            @PathVariable Integer id, @RequestBody UserDto userDto
//    ){
//
//        return userService.updateUserById(id, userDto);
//    }



    @GetMapping()
    public RegisterUserDto getCerrentUser(@AuthenticationPrincipal UserDetails userDetails){

        return userService.getCurrentUser(userDetails);
    }

    @PutMapping()
    public RegisterUserDto updateCurrentUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RegisterUserDto registerUserDto){

        return userService.updateCurrentUser(userDetails, registerUserDto);
    }

    @DeleteMapping()
    public RegisterUserDto deleteCurrentUser(@AuthenticationPrincipal UserDetails userDetails){

        return userService.deleteCurrentUser(userDetails);
    }
}
