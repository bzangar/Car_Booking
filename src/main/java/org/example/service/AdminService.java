package org.example.service;

import org.example.model.dto.*;

import java.util.List;

public interface AdminService {
    List<UserDto> getAllUsers();

    List<CarDto> getAllCars();

    UserResponceDto deleteUserById(Integer id);
}
