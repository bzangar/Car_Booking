package org.example;

import org.example.exception.CarNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;
import org.example.model.entity.Car;
import org.example.model.entity.User;
import org.example.repository.CarRepository;
import org.example.service.CarServiceImpl;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @Mock
    private Mapper mapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private CarServiceImpl carService;

    private CarDto carDto;
    private Car car;
    private User user;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder().id(1).username("testuser").build();
        userDetails = org.springframework.security.core.userdetails.User
                .withUsername("testuser")
                .password("pass")
                .roles("USER")
                .build();

        carDto = CarDto.builder()
                .id(1)
                .brand("Toyota")
                .model("Camry")
                .location("Almaty")
                .pricePerDay(BigDecimal.valueOf(100))
                .user(UserDto.builder().id(1).username("testuser").build())
                .build();

        car = Car.builder()
                .id(1)
                .brand("Toyota")
                .model("Camry")
                .location("Almaty")
                .pricePerDay(BigDecimal.valueOf(100))
                .user(user)
                .build();
    }

    @Test
    void testCreateCar() {
        when(userService.getUserByUsername("testuser")).thenReturn(user);
        when(mapper.carFromEntityToDto(any(Car.class))).thenReturn(carDto);

        CarDto result = carService.createCar(carDto, userDetails);

        assertEquals("Toyota", result.getBrand());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    void testGetAllCar() {
        when(carRepository.findAll()).thenReturn(List.of(car));
        when(mapper.userFromEntityToDto(user)).thenReturn(carDto.getUser());

        List<CarDto> result = carService.getAllCar();

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
    }

    @Test
    void testGetCarDtoById_Found() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        when(mapper.carFromEntityToDto(car)).thenReturn(carDto);

        CarDto result = carService.getCarDtoById(1);

        assertEquals("Toyota", result.getBrand());
    }

    @Test
    void testGetCarDtoById_NotFound() {
        when(carRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(CarNotFoundException.class, () -> carService.getCarDtoById(1));
    }

    @Test
    void testEditCar() {
        when(carRepository.findById(1)).thenReturn(Optional.of(car));
        when(mapper.carFromEntityToDto(any(Car.class))).thenReturn(carDto);

        CarDto result = carService.editCar(1, carDto);

        assertEquals("Toyota", result.getBrand());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testDeleteCarById_Found() {
        when(carRepository.existsById(1)).thenReturn(true);

        boolean result = carService.deleteCarById(1);

        assertTrue(result);
        verify(carRepository).deleteById(1);
    }

    @Test
    void testDeleteCarById_NotFound() {
        when(carRepository.existsById(1)).thenReturn(false);

        assertThrows(CarNotFoundException.class, () -> carService.deleteCarById(1));
    }

    @Test
    void testGetAllCarOfOwner() {
        when(carRepository.findByUser_Username("testuser")).thenReturn(List.of(car));
        when(mapper.carFromEntityToDto(car)).thenReturn(carDto);

        List<CarDto> result = carService.getAllCarOfOwner(userDetails);

        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
    }
}
