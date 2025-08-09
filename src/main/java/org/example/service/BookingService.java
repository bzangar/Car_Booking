package org.example.service;

import org.example.model.dto.BookingDto;
import org.example.model.dto.BookingResponseDto;
import org.springframework.security.core.userdetails.UserDetails;


import java.math.BigDecimal;
import java.util.List;

public interface BookingService {
    BookingResponseDto createBooking(BookingDto bookingDto, UserDetails userDetails);

    BookingResponseDto getBookingById(Integer id);

    BookingResponseDto changeTheStatus(Integer id, BookingDto bookingDto);

    List<BookingResponseDto> getAllBookingsByUserId(Integer userId);

    BigDecimal calculatePrice(BookingDto bookingDto);

    List<BookingResponseDto> getAllBookingsByUserDetails(UserDetails userDetails);
}
