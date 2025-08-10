package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.CarController;
import org.example.model.dto.CarDto;
import org.example.service.CarService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public CarService carService() {
            return Mockito.mock(CarService.class);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                    .csrf(AbstractHttpConfigurer::disable);
            return http.build();
        }
    }

    @Test
    @WithMockUser(username = "zangar", roles = "USER")
    void testCreateCar() throws Exception {
        CarDto input = CarDto.builder()
                .brand("Toyota")
                .model("Camry")
                .location("Almaty")
                .pricePerDay(new BigDecimal(100))
                .build();

        CarDto output = CarDto.builder()
                .id(1)
                .brand("Toyota")
                .model("Camry")
                .location("Almaty")
                .pricePerDay(new BigDecimal(100))
                .build();

        Mockito.when(carService.createCar(any(CarDto.class), any(UserDetails.class)))
                .thenReturn(output);

        mockMvc.perform(post("/api/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));
    }

    @Test
    @WithMockUser
    void testGetAllCars() throws Exception {
        List<CarDto> cars = List.of(
                CarDto.builder().id(1).brand("BMW").model("X5").location("Astana").pricePerDay(new BigDecimal(200)).build(),
                CarDto.builder().id(2).brand("Audi").model("A4").location("Almaty").pricePerDay(new BigDecimal(150)).build()
        );

        Mockito.when(carService.getAllCar()).thenReturn(cars);

        mockMvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("BMW"))
                .andExpect(jsonPath("$[1].brand").value("Audi"));
    }

    @Test
    @WithMockUser
    void testGetCarById() throws Exception {
        CarDto car = CarDto.builder()
                .id(1)
                .brand("Mercedes")
                .model("C-Class")
                .location("Shymkent")
                .pricePerDay(new BigDecimal(250))
                .build();

        Mockito.when(carService.getCarDtoById(1)).thenReturn(car);

        mockMvc.perform(get("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Mercedes"));
    }

    @Test
    @WithMockUser
    void testEditCarById() throws Exception {
        CarDto updated = CarDto.builder()
                .id(1)
                .brand("Tesla")
                .model("Model S")
                .location("Almaty")
                .pricePerDay(new BigDecimal(300))
                .build();

        Mockito.when(carService.editCar(eq(1), any(CarDto.class))).thenReturn(updated);

        mockMvc.perform(put("/api/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand").value("Tesla"));
    }

    @Test
    @WithMockUser
    void testDeleteCarById() throws Exception {
        Mockito.when(carService.deleteCarById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/cars/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
}
