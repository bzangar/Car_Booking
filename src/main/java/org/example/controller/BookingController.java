package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.BookingDto;
import org.example.model.dto.BookingResponseDto;
import org.example.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookingController {

    final private BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponseDto createBooking(
            @RequestBody BookingDto bookingDto
    ){
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping("/booking/{id}")
    public BookingResponseDto getBookingById(
            @PathVariable Integer id
    ){
        return bookingService.getBookingById(id);
    }

    @PutMapping("/booking/{id}/status")
    public BookingResponseDto changeStatus(
            @PathVariable Integer id, @RequestBody BookingDto bookingDto
    ){
        return bookingService.changeTheStatus(id, bookingDto);
    }

    @GetMapping("/users/{id}/bookings")
    public List<BookingResponseDto> getAllBookingsByUserId(
            @PathVariable Integer id
    ){
        return bookingService.getAllBookingsByUserId(id);
    }
}
