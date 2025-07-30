package org.example.model.dto;

import lombok.*;


@Data
@Builder
public class ReviewDto {
    private Integer id;
    private String comment;
    private Integer rating;
    private UserDto user;
    private CarDto car;
}

