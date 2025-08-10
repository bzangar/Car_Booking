package org.example.service;

import org.example.model.dto.ReviewDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Integer carId, ReviewDto reviewDto, UserDetails userDetails);

    List<ReviewDto> getReviewsByCarId(Integer id);

    ReviewDto deleteReviewById(Integer id);
}
