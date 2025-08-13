package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Car", description = "Операции с автомобилями")
public class CarController {

    final private CarService carService;


    @Operation(
            summary = "Создать автомобиль",
            description = "Создаёт новый автомобиль, привязанный к текущему владельцу.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные автомобиля",
                    required = true,
                    content = @Content(schema = @Schema(implementation = CarDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "brand": "Toyota",
                                      "model": "Camry",
                                      "location": "Almaty",
                                      "pricePerDay": 15000
                                    }
                                    """))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Автомобиль успешно создан",
                            content = @Content(schema = @Schema(implementation = CarDto.class))),
                    @ApiResponse(responseCode = "401", description = "Неавторизован")
            }
    )
    @PostMapping()
    public CarDto createCar(
            @RequestBody CarDto carDto , @AuthenticationPrincipal UserDetails userDetails
            )
    {

        return carService.createCar(carDto, userDetails);
    }


    @Operation(
            summary = "Получить список всех автомобилей",
            description = "Возвращает список всех автомобилей в системе.",
            responses = @ApiResponse(responseCode = "200", description = "Успешно получено")
    )
    @GetMapping()
    public List<CarDto> getAllCars()
    {

        return carService.getAllCar();
    }


    @Operation(
            summary = "Получить автомобили текущего владельца",
            description = "Возвращает список автомобилей, принадлежащих авторизованному владельцу."
    )
    @GetMapping("/owner")
    public List<CarDto> getAllCarsOfOwner(@AuthenticationPrincipal UserDetails userDetails)
    {

        return carService.getAllCarOfOwner(userDetails);
    }


    @Operation(
            summary = "Получить автомобиль по ID",
            description = "Возвращает информацию об автомобиле по его ID."
    )
    @GetMapping("/{id}")
    public CarDto getCarById(
            @PathVariable Integer id)
    {

        return carService.getCarDtoById(id);
    }


    @Operation(
            summary = "Редактировать автомобиль",
            description = "Обновляет данные автомобиля по ID.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(schema = @Schema(implementation = CarDto.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "brand": "Kia",
                                      "model": "Sportage",
                                      "location": "Astana",
                                      "pricePerDay": 20000
                                    }
                                    """))
            )
    )
    @PutMapping("/{id}")
    public CarDto editCarById(
            @PathVariable Integer id, @RequestBody CarDto carDto)
    {

        return carService.editCar(id, carDto);
    }


    @Operation(
            summary = "Удалить автомобиль",
            description = "Удаляет автомобиль по ID."
    )
    @DeleteMapping("/{id}")
    public boolean deleteCarById(
            @PathVariable Integer id)
    {

        return carService.deleteCarById(id);
    }
}