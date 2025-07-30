package org.example.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
public class CarDto {
    private Integer id;
    private String brand;
    private String model;
    private String location;
    private BigDecimal pricePerDay;
    private UserDto user;
}
