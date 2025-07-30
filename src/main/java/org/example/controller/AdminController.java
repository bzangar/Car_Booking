package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    final private AdminService adminService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers(){

        return adminService.getAllUsers();
    }

    @GetMapping("/cars")
    public List<CarDto> getAllCars(){

        return adminService.getAllCars();
    }
}
