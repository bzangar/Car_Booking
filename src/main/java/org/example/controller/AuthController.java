package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.model.Mapper;
import org.example.model.dto.UserDto;
import org.example.model.dto.UserResponceDto;
import org.example.model.entity.User;
import org.example.repository.UserRepository;
import org.example.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    final private PasswordEncoder passwordEncoder;
    final private UserService userService;
    final private Mapper mapper;
    final private UserRepository userRepository;

    @PostMapping("/register")
    public UserDto registrationProcess(@RequestBody UserDto userDto){

        if(userService.existsByUsername(userDto.getUsername())){
            throw new IllegalStateException("Пользователь занят и уже используется");
        }

        userService.crateUser(userDto);

        return userDto;
    }

//    @PostMapping("/login")
//    public String login(@RequestBody UserDto userDto){
//        User user = userRepository.findByUsername(userDto.getUsername());
//
//        if(user == null){
//            return "Неверный логин и пароль";
//        }
//
//        if(!passwordEncoder.matches(userDto.getPassword(), user.getPassword())){
//            return "Неверный логин и пароль";
//        }
//
//        return "Успешный вход";
//    }

//    @PostMapping("/logout")
//    public


}
