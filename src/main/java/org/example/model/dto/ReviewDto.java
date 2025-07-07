package org.example.model.dto;

import lombok.Builder;
import lombok.Data;
import org.example.model.entity.Car;
import org.example.model.entity.User;

@Data
@Builder
public class ReviewDto {
    private Integer id;
    private String comment;
    private User user;
    private Car car;
    private Integer rating;
}
