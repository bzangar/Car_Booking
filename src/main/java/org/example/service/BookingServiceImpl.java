package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.BookingNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.BookingDto;
import org.example.model.entity.Booking;
import org.example.repository.BookingRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    final private BookingRepository bookingRepository;
    final private CarService carService;
    final private UserService userService;
    final private Mapper mapper;

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {
        Booking booking = Booking.builder()
                .car(carService.getCarById(bookingDto.getCar().getId()))
                .user(userService.getUserById(bookingDto.getUser().getId()))
                .endTime(bookingDto.getEndTime())
                .startTime(bookingDto.getStartTime())
                .status(bookingDto.getStatus())
                .build();
        bookingRepository.save(booking);
        return mapper.bookingFromEntityToDto(booking);
    }

    @Override
    public BookingDto getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new BookingNotFoundException("Booking does not exists!!"));
        return mapper.bookingFromEntityToDto(booking);
    }

    @Override
    public BookingDto changeTheStatus(Integer id, BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new BookingNotFoundException("Booking does not exists!!"));
        booking.setStatus(bookingDto.getStatus());
        bookingRepository.save(booking);
        return mapper.bookingFromEntityToDto(booking);
    }

    @Override
    public List<BookingDto> getAllBookingsByUserId(Integer userId) {
        return bookingRepository.findAllByUserId(userId)
                .stream()
                .map(booking-> mapper.bookingFromEntityToDto(booking))
                .toList();
    }
}
