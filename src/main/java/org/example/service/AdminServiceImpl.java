package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Mapper;
import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;
import org.example.repository.CarRepository;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    final private UserRepository userRepository;
    final private CarRepository carRepository;
    final private Mapper mapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> mapper.userFromEntityToDto(user))
                .toList();
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(car -> mapper.carFromEntityToDto(car))
                .toList();
    }
}
