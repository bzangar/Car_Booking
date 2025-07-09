package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CarDto;
import org.example.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    final private CarService carService;

    @PostMapping("")
    public CarDto createCar(
            @RequestBody CarDto carDto
    ){
      return carService.createCar(carDto);
    }

    @GetMapping("")
    public List<CarDto> getAllCars(){
        return carService.getAllCar();
    }

    @GetMapping("/{id}")
    public CarDto getCarById(
            @PathVariable Integer id
    ){
        return carService.getCarDtoById(id);
    }

    @PutMapping("/{id}")
    public CarDto editCarById(
            @PathVariable Integer id, @RequestBody CarDto carDto
    ){
        return carService.editCar(id, carDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCarById(
            @PathVariable Integer id
    ){
        return carService.deleteCarById(id);
    }

}
