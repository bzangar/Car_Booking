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

    @PostMapping("/booking")
    public BookingResponseDto createBooking(
            @RequestBody BookingDto bookingDto, @AuthenticationPrincipal UserDetails userDetails
            ){

        return bookingService.createBooking(bookingDto, userDetails);
    }

    @GetMapping("/booking/{id}")
    public BookingResponseDto getBookingById(
            @PathVariable Integer id
    ){

        return bookingService.getBookingById(id);
    }

    @PutMapping("/booking/{id}/status")
    public BookingResponseDto changeStatus(
            @PathVariable Integer id, @Valid @RequestBody BookingDto bookingDto
    ){

        return bookingService.changeTheStatus(id, bookingDto);
    }

    @GetMapping("/users/me/booking")
    public List<BookingResponseDto> getAllBookingsByUserId(
            @AuthenticationPrincipal UserDetails userDetails
    ){

        return bookingService.getAllBookingsByUserDetails(userDetails);
    }

    @GetMapping("/owner/booking")
    public List<BookingResponseDto> getAllBookingsOfOwner(
            @AuthenticationPrincipal UserDetails userDetails
    ){

        return bookingService.getAllBookingsOfOwner(userDetails);
    }
}
