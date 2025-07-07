package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Mapper;
import org.example.model.dto.CarDto;
import org.example.model.entity.Car;
import org.example.repository.CarRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    final private CarRepository carRepository;
    final private Mapper mapper;

    @Override
    public CarDto createCar(CarDto carDto) {
        Car car = Car.builder()
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .location(carDto.getLocation())
                .pricePerDay(carDto.getPricePerDay())
                .user(carDto.getUser())
                .build();
        carRepository.save(car);
        return mapper.carFromEntityToDto(car);
    }
}
