package org.example.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.model.enums.BookingStatus;

import java.time.LocalDate;

@Table(name = "bookings")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "car_id")
    @ManyToOne
    private Car car;
    private LocalDate startTime;
    private LocalDate endTime;
    private String status;
}
