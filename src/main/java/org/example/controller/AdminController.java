package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;
import org.example.model.dto.UserResponceDto;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    final private AdminService adminService;

    @GetMapping("/users")
    public List<UserDto> getAllUsers(){

        return adminService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public UserResponceDto deleteUserById(@PathVariable Integer id){

        return adminService.deleteUserById(id);
    }

    @GetMapping("/cars")
    public List<CarDto> getAllCars(){

        return adminService.getAllCars();
    }
}
