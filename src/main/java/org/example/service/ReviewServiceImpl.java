package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.ReviewNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.ReviewDto;
import org.example.model.entity.Review;
import org.example.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    final private ReviewRepository reviewRepository;
    final private CarService carService;
    final private UserService userService;
    final private Mapper mapper;

    @Override
    public ReviewDto createReview(Integer carId, ReviewDto reviewDto) {
        Review review = Review.builder()
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
                .car(carService.getCarById(carId))
                .user(userService.getUserById(reviewDto.getUser().getId()))
                .build();
        reviewRepository.save(review);

        return mapper.reviewFromEntityToDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByCarId(Integer id) {
        List<Review> list = reviewRepository.findByCarId(id);

        return list.stream()
                .map(review-> mapper.reviewFromEntityToDto(review)).toList();
    }

    @Override
    public ReviewDto deleteReviewById(Integer id) {
        Review result = reviewRepository.findById(id)
                        .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        reviewRepository.deleteById(id);

        return mapper.reviewFromEntityToDto(result);
    }
}
