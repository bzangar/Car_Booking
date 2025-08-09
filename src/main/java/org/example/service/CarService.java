package org.example.service;

import org.example.model.dto.CarDto;
import org.example.model.entity.Car;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CarService {

    CarDto createCar(CarDto carDto, UserDetails userDetails);

    List<CarDto> getAllCar();

    CarDto getCarDtoById(Integer id);

    CarDto editCar(Integer id, CarDto carDto);

    boolean deleteCarById(Integer id);

    Car getCarById(Integer id);
}
