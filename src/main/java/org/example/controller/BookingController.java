package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.model.dto.BookingDto;
import org.example.model.dto.BookingResponseDto;
import org.example.service.BookingService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookingController {

    final private BookingService bookingService;

    @PostMapping("/bookings")
    public BookingResponseDto createBooking(
            @RequestBody BookingDto bookingDto, @AuthenticationPrincipal UserDetails userDetails
            ){

        return bookingService.createBooking(bookingDto, userDetails);
    }

    @GetMapping("/bookings/{id}")
    public BookingResponseDto getBookingById(
            @PathVariable Integer id
    ){

        return bookingService.getBookingById(id);
    }

    @DeleteMapping("/bookings/{id}")
    public BookingResponseDto deleteBookingById(
            @PathVariable Integer id
    ){

        return bookingService.deleteBookingById(id);
    }

    @PutMapping("/bookings/{id}/status")
    public BookingResponseDto changeStatus(
            @PathVariable Integer id, @Valid @RequestBody BookingDto bookingDto
    ){

        return bookingService.changeTheStatus(id, bookingDto);
    }

    @GetMapping("/users/me/bookings")
    public List<BookingResponseDto> getAllBookingsByUserId(
            @AuthenticationPrincipal UserDetails userDetails
    ){

        return bookingService.getAllBookingsByUserDetails(userDetails);
    }

    @GetMapping("/owner/bookings")
    public List<BookingResponseDto> getAllBookingsOfOwner(
            @AuthenticationPrincipal UserDetails userDetails
    ){

        return bookingService.getAllBookingsOfOwner(userDetails);
    }
}
