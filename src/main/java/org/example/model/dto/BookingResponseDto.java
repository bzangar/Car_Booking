package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDto {
    private Integer id;
    private UserDto user;
    private CarDto car;
    private BigDecimal totalPrice;
    private LocalDate startTime;
    private LocalDate endTime;
    private String status;
}
