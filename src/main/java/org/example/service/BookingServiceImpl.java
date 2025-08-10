package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.BookingNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.BookingDto;
import org.example.model.dto.BookingResponseDto;
import org.example.model.entity.Booking;
import org.example.model.entity.Car;
import org.example.model.entity.User;
import org.example.repository.BookingRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    final private BookingRepository bookingRepository;
    final private CarService carService;
    final private UserService userService;
    final private Mapper mapper;

    @Override
    public BookingResponseDto createBooking(BookingDto bookingDto, UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.getUserByUsername(username);

        Booking booking = Booking.builder()
                .car(carService.getCarById(bookingDto.getCar().getId()))
                .user(user)
                .totalPrice(calculatePrice(bookingDto))
                .endTime(bookingDto.getEndTime())
                .startTime(bookingDto.getStartTime())
                .status("В ожидании")
                .build();
        bookingRepository.save(booking);

        return mapper.bookingFromEntityToResponseDto(booking);
    }

    @Override
    public BookingResponseDto getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new BookingNotFoundException("Booking does not exists!!"));

        return mapper.bookingFromEntityToResponseDto(booking);
    }

    @Override
    public BookingResponseDto changeTheStatus(Integer id, BookingDto bookingDto) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(()-> new BookingNotFoundException("Booking does not exists!!"));
        booking.setStatus(bookingDto.getStatus());
        bookingRepository.save(booking);

        return mapper.bookingFromEntityToResponseDto(booking);
    }

    @Override
    public BigDecimal calculatePrice(BookingDto bookingDto) {
        LocalDate startTime = bookingDto.getStartTime();
        LocalDate endTime = bookingDto.getEndTime();

        long days = ChronoUnit.DAYS.between(startTime, endTime);

        Car car = carService.getCarById(bookingDto.getCar().getId());
        BigDecimal pricePerDay = car.getPricePerDay();

        if(pricePerDay == null){
            throw new IllegalStateException("Car pricePerDay is null");
        }

        return BigDecimal.valueOf(days).multiply(pricePerDay);
    }

    @Override
    public List<BookingResponseDto> getAllBookingsByUserDetails(UserDetails userDetails) {
        String username = userDetails.getUsername();

        return bookingRepository.findByUserUsername(username)
                .stream()
                .map(booking -> mapper.bookingFromEntityToResponseDto(booking))
                .toList();
    }
}
