package org.example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Integer id;
    private UserDto user;
    private CarDto car;
    private LocalDate startTime;
    private LocalDate endTime;
    private String status;
}
