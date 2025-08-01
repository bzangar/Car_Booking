package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.CarDto;
import org.example.service.CarService;
import org.example.service.UserService;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    final private CarService carService;

    @PostMapping()
    public CarDto createCar(
            @RequestBody CarDto carDto , @AuthenticationPrincipal UserDetails userDetails
            )
    {
        String username = userDetails.getUsername();
        System.out.println("USERNAME FROM CONTEXT: " + username);

        return carService.createCar(carDto, username);
    }

    @GetMapping()
    public List<CarDto> getAllCars()
    {

        return carService.getAllCar();
    }

    @GetMapping("/{id}")
    public CarDto getCarById(
            @PathVariable Integer id)
    {

        return carService.getCarDtoById(id);
    }

    @PutMapping("/{id}")
    public CarDto editCarById(
            @PathVariable Integer id, @RequestBody CarDto carDto)
    {

        return carService.editCar(id, carDto);
    }

    @DeleteMapping("/{id}")
    public boolean deleteCarById(
            @PathVariable Integer id)
    {

        return carService.deleteCarById(id);
    }
}