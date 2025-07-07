package org.example.model;

import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;
import org.example.model.entity.Car;
import org.example.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserDto userFromEntityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public UserDto userResponceFromEntityToDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public CarDto carFromEntityToDto(Car car){
        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .brand(car.getBrand())
                .pricePerDay(car.getPricePerDay())
                .location(car.getLocation())
                .user(car.getUser())
                .build();
    }
}
