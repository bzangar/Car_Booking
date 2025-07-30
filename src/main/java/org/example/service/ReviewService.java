package org.example.service;

import org.example.model.dto.ReviewDto;

import java.util.List;

public interface ReviewService {
    ReviewDto createReview(Integer carId, ReviewDto reviewDto);

    List<ReviewDto> getReviewsByCarId(Integer id);

    ReviewDto deleteReviewById(Integer id);
}
