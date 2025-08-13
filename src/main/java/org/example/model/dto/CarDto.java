package org.example.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Schema(description = "Модель данных автомобиля")
public class CarDto {

    @Schema(description = "Уникальный идентификатор автомобиля", example = "1")
    private Integer id;

    @Schema(description = "Марка автомобиля", example = "Toyota")
    private String brand;

    @Schema(description = "Модель автомобиля", example = "Camry")
    private String model;

    @Schema(description = "Местоположение автомобиля", example = "Almaty")
    private String location;

    @Schema(description = "Цена аренды в день", example = "15000")
    private BigDecimal pricePerDay;

    @Schema(description = "Владелец автомобиля")
    private UserDto user;
}
