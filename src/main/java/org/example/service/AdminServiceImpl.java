package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.UserNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.CarDto;
import org.example.model.dto.LoginUserDto;
import org.example.model.dto.UserDto;
import org.example.model.dto.UserResponceDto;
import org.example.model.entity.User;
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

    @Override
    public UserResponceDto deleteUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        UserResponceDto result = UserResponceDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();

        userRepository.delete(user);

        return result;
    }
}
