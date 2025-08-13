package org.example;

import org.example.exception.BookingNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.BookingDto;
import org.example.model.dto.BookingResponseDto;
import org.example.model.entity.Booking;
import org.example.model.entity.Car;
import org.example.model.entity.User;
import org.example.model.enums.BookingStatus;
import org.example.repository.BookingRepository;
import org.example.service.BookingServiceImpl;
import org.example.service.CarService;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private CarService carService;
    @Mock
    private UserService userService;
    @Mock
    private Mapper mapper;
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Car car;
    private User user;
    private Booking booking;
    private BookingDto bookingDto;
    private BookingResponseDto bookingResponseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        car = Car.builder()
                .id(1)
                .pricePerDay(BigDecimal.valueOf(100))
                .build();

        user = User.builder()
                .id(1)
                .username("testUser")
                .build();

        booking = Booking.builder()
                .id(1)
                .car(car)
                .user(user)
                .status(BookingStatus.PENDING)
                .totalPrice(BigDecimal.valueOf(200))
                .startTime(LocalDate.of(2025, 1, 1))
                .endTime(LocalDate.of(2025, 1, 3))
                .build();

        bookingDto = BookingDto.builder()
                .car(org.example.model.dto.CarDto.builder().id(1).build())
                .startTime(LocalDate.of(2025, 1, 1))
                .endTime(LocalDate.of(2025, 1, 3))
                .status(BookingStatus.PENDING)
                .build();

        bookingResponseDto = BookingResponseDto.builder()
                .id(1)
                .totalPrice(BigDecimal.valueOf(200))
                .status(BookingStatus.PENDING)
                .build();

        when(userDetails.getUsername()).thenReturn("testUser");
    }

    @Test
    void createBooking_ShouldReturnBookingResponseDto() {
        when(userService.getUserByUsername("testUser")).thenReturn(user);
        when(carService.getCarById(1)).thenReturn(car);
        when(mapper.bookingFromEntityToResponseDto(any())).thenReturn(bookingResponseDto);

        BookingResponseDto result = bookingService.createBooking(bookingDto, userDetails);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200), result.getTotalPrice());
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void getBookingById_ShouldReturnBookingResponseDto() {
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(mapper.bookingFromEntityToResponseDto(booking)).thenReturn(bookingResponseDto);

        BookingResponseDto result = bookingService.getBookingById(1);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200), result.getTotalPrice());
    }

    @Test
    void getBookingById_ShouldThrowException_WhenNotFound() {
        when(bookingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(BookingNotFoundException.class, () -> bookingService.getBookingById(1));
    }

    @Test
    void changeTheStatus_ShouldUpdateStatus() {
        when(bookingRepository.findById(1)).thenReturn(Optional.of(booking));
        when(mapper.bookingFromEntityToResponseDto(booking)).thenReturn(bookingResponseDto);

        BookingResponseDto result = bookingService.changeTheStatus(1, bookingDto);

        assertNotNull(result);
        verify(bookingRepository, times(1)).save(booking);
        assertEquals(BookingStatus.PENDING, booking.getStatus());
    }

    @Test
    void calculatePrice_ShouldReturnCorrectValue() {
        when(carService.getCarById(1)).thenReturn(car);

        BigDecimal price = bookingService.calculatePrice(bookingDto);

        assertEquals(BigDecimal.valueOf(200), price);
    }

    @Test
    void calculatePrice_ShouldThrowException_WhenPriceNull() {
        car.setPricePerDay(null);
        when(carService.getCarById(1)).thenReturn(car);

        assertThrows(IllegalStateException.class, () -> bookingService.calculatePrice(bookingDto));
    }

    @Test
    void getAllBookingsByUserDetails_ShouldReturnList() {
        when(bookingRepository.findByUserUsername("testUser")).thenReturn(List.of(booking));
        when(mapper.bookingFromEntityToResponseDto(booking)).thenReturn(bookingResponseDto);

        List<BookingResponseDto> result = bookingService.getAllBookingsByUserDetails(userDetails);

        assertEquals(1, result.size());
    }

    @Test
    void getAllBookingsOfOwner_ShouldReturnList() {
        when(bookingRepository.findByCar_User_Username("testUser")).thenReturn(List.of(booking));
        when(mapper.bookingFromEntityToResponseDto(booking)).thenReturn(bookingResponseDto);

        List<BookingResponseDto> result = bookingService.getAllBookingsOfOwner(userDetails);

        assertEquals(1, result.size());
    }
}
