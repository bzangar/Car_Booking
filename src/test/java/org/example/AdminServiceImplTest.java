package org.example;

import org.example.model.Mapper;
import org.example.model.dto.CarDto;
import org.example.model.dto.UserDto;
import org.example.model.entity.Car;
import org.example.model.entity.User;
import org.example.repository.CarRepository;
import org.example.repository.UserRepository;
import org.example.service.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_ShouldReturnMappedUserDtos() {
        // given
        User user1 = User.builder().id(1).username("zangar").fullName("Zangar Bokenov").build();
        User user2 = User.builder().id(2).username("jane").fullName("Jane Doe").build();
        UserDto dto1 = UserDto.builder().id(1).username("zangar").fullName("Zangar Bokenov").build();
        UserDto dto2 = UserDto.builder().id(2).username("jane").fullName("Jane Doe").build();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(mapper.userFromEntityToDto(user1)).thenReturn(dto1);
        when(mapper.userFromEntityToDto(user2)).thenReturn(dto2);

        // when
        List<UserDto> result = adminService.getAllUsers();

        // then
        assertEquals(2, result.size());
        assertEquals("zangar", result.get(0).getUsername());
        assertEquals("jane", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
        verify(mapper, times(1)).userFromEntityToDto(user1);
        verify(mapper, times(1)).userFromEntityToDto(user2);
    }

    @Test
    void getAllCars_ShouldReturnMappedCarDtos() {
        // given
        Car car1 = Car.builder().id(1).brand("Toyota").model("Camry").pricePerDay(BigDecimal.valueOf(50)).build();
        Car car2 = Car.builder().id(2).brand("BMW").model("X5").pricePerDay(BigDecimal.valueOf(120)).build();
        CarDto dto1 = CarDto.builder().id(1).brand("Toyota").model("Camry").pricePerDay(BigDecimal.valueOf(50)).build();
        CarDto dto2 = CarDto.builder().id(2).brand("BMW").model("X5").pricePerDay(BigDecimal.valueOf(120)).build();

        when(carRepository.findAll()).thenReturn(List.of(car1, car2));
        when(mapper.carFromEntityToDto(car1)).thenReturn(dto1);
        when(mapper.carFromEntityToDto(car2)).thenReturn(dto2);

        // when
        List<CarDto> result = adminService.getAllCars();

        // then
        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
        assertEquals("BMW", result.get(1).getBrand());
        verify(carRepository, times(1)).findAll();
        verify(mapper, times(1)).carFromEntityToDto(car1);
        verify(mapper, times(1)).carFromEntityToDto(car2);
    }
}
