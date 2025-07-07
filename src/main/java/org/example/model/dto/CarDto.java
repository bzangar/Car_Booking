package org.example.model.dto;

import lombok.Builder;
import lombok.Data;
import org.example.model.entity.User;

import java.math.BigDecimal;

@Data
@Builder
public class CarDto {
    private Integer id;
    private String brand;
    private String model;
    private String location;
    private BigDecimal pricePerDay;
    private User user;
}
