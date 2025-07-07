package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CarDto;
import org.example.repository.CarRepository;
import org.example.service.CarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    final private CarService carService;

    @PostMapping("/create")
    public CarDto createCar(
            @RequestBody CarDto carDto
    ){
      return carService.createCar(carDto);
    }
}
