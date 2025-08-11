package org.example.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.enums.BookingStatus;

import java.math.BigDecimal;
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
    private BigDecimal totalPrice;
    private LocalDate startTime;
    private LocalDate endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
