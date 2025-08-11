package org.example.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.model.enums.BookingStatus;

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

    @NotNull(message = "Status cannot be null")
    private BookingStatus status;
}
