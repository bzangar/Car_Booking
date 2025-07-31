package org.example.model;

import org.example.model.dto.*;
import org.example.model.entity.Booking;
import org.example.model.entity.Car;
import org.example.model.entity.Review;
import org.example.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public UserDto userFromEntityToDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public UserDto userResponceFromEntityToDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .role(user.getRole())
                .build();
    }

    public CarDto carFromEntityToDto(Car car){

        return CarDto.builder()
                .id(car.getId())
                .model(car.getModel())
                .brand(car.getBrand())
                .pricePerDay(car.getPricePerDay())
                .location(car.getLocation())
                .user(userFromEntityToDto(car.getUser()))
                .build();
    }

    public BookingDto bookingFromEntityToDto(Booking booking){

        return BookingDto.builder()
                .id(booking.getId())
                .car(carFromEntityToDto(booking.getCar()))
                .user(userFromEntityToDto(booking.getUser()))
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .build();
    }

    public BookingResponseDto bookingFromEntityToResponseDto(Booking booking){

        return BookingResponseDto.builder()
                .id(booking.getId())
                .car(carFromEntityToDto(booking.getCar()))
                .user(userFromEntityToDto(booking.getUser()))
                .totalPrice(booking.getTotalPrice())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .status(booking.getStatus())
                .build();
    }

    public ReviewDto reviewFromEntityToDto(Review review){

        return ReviewDto.builder()
                .id(review.getId())
                .car(carFromEntityToDto(review.getCar()))
                .user(userFromEntityToDto(review.getUser()))
                .comment(review.getComment())
                .rating(review.getRating())
                .build();
    }
}
