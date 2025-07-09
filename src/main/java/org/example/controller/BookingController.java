package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.BookingDto;
import org.example.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class BookingController {

    final private BookingService bookingService;

    @PostMapping("/booking")
    public BookingDto createBooking(
            @RequestBody BookingDto bookingDto
    ){
        return bookingService.createBooking(bookingDto);
    }

    @GetMapping("/booking/{id}")
    public BookingDto getBookingById(
            @PathVariable Integer id
    ){
        return bookingService.getBookingById(id);
    }

    @PutMapping("/booking/{id}/status")
    public BookingDto changeStatus(
            @PathVariable Integer id, @RequestBody BookingDto bookingDto
    ){
        return bookingService.changeTheStatus(id, bookingDto);
    }

    @GetMapping("/users/{id}/bookings")
    public List<BookingDto> getAllBookingsByUserId(
            @PathVariable Integer id
    ){
        return bookingService.getAllBookingsByUserId(id);
    }
}
