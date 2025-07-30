package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.dto.ReviewDto;
import org.example.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {
    final private ReviewService reviewService;

    @PostMapping("/cars/{carId}/reviews")
    public ReviewDto createReview(
            @PathVariable Integer carId, @RequestBody ReviewDto reviewDto
    ){

        return reviewService.createReview(carId, reviewDto);
    }

    @GetMapping("/cars/{carId}/reviews")
    public List<ReviewDto> getReviewById(
            @PathVariable Integer carId
    ){

        return reviewService.getReviewsByCarId(carId);
    }

    @DeleteMapping("/reviews/{id}")
    public ReviewDto deleteReviewById(@PathVariable Integer id){

        return reviewService.deleteReviewById(id);
    }
}
