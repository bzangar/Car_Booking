package org.example.model.dto;

import lombok.Builder;
import lombok.Data;
import org.example.model.entity.Car;
import org.example.model.entity.User;

import java.time.LocalDate;

@Data
@Builder
public class BookingDto {
    private Integer id;
    private User user;
    private Car car;
    private LocalDate startTime;
    private LocalDate endTime;
    private String status;
}
