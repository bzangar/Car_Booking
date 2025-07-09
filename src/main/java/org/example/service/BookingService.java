package org.example.service;

import org.example.model.dto.BookingDto;


import java.util.List;

public interface BookingService {
    BookingDto createBooking(BookingDto bookingDto);

    BookingDto getBookingById(Integer id);

    BookingDto changeTheStatus(Integer id, BookingDto bookingDto);

    List<BookingDto> getAllBookingsByUserId(Integer userId);
}
