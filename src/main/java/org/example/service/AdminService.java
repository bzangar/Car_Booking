package org.example.service;

import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;

import java.util.List;

public interface AdminService {
    List<UserDto> getAllUsers();

    List<CarDto> getAllCars();
}
