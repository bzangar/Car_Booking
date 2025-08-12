package org.example;

import org.example.exception.ReviewNotFoundException;
import org.example.model.Mapper;
import org.example.model.dto.ReviewDto;
import org.example.model.entity.Car;
import org.example.model.entity.Review;
import org.example.model.entity.User;
import org.example.repository.ReviewRepository;
import org.example.service.CarService;
import org.example.service.ReviewServiceImpl;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CarService carService;

    @Mock
    private UserService userService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private UserDetails userDetails;

    private User user;
    private Car car;
    private Review review;
    private ReviewDto reviewDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .id(1)
                .username("zangar")
                .fullName("Zangar Bokenov")
                .build();

        car = Car.builder()
                .id(10)
                .build();

        review = Review.builder()
                .id(100)
                .comment("Great car!")
                .rating(5)
                .car(car)
                .user(user)
                .build();

        reviewDto = ReviewDto.builder()
                .id(100)
                .comment("Great car!")
                .rating(5)
                .build();
    }

    @Test
    void testCreateReview() {
        when(userDetails.getUsername()).thenReturn("zangar");
        when(userService.getUserByUsername("zangar")).thenReturn(user);
        when(carService.getCarById(10)).thenReturn(car);
        when(mapper.reviewFromEntityToDto(any(Review.class))).thenReturn(reviewDto);

        ReviewDto result = reviewService.createReview(10, reviewDto, userDetails);

        assertEquals("Great car!", result.getComment());
        assertEquals(5, result.getRating());

        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        verify(reviewRepository, times(1)).save(captor.capture());
        assertEquals("Great car!", captor.getValue().getComment());
        assertEquals(user, captor.getValue().getUser());
    }

    @Test
    void testGetReviewsByCarId() {
        when(reviewRepository.findByCarId(10)).thenReturn(List.of(review));
        when(mapper.reviewFromEntityToDto(review)).thenReturn(reviewDto);

        List<ReviewDto> result = reviewService.getReviewsByCarId(10);

        assertEquals(1, result.size());
        assertEquals("Great car!", result.get(0).getComment());
        verify(reviewRepository, times(1)).findByCarId(10);
    }

    @Test
    void testDeleteReviewById_Found() {
        when(reviewRepository.findById(100)).thenReturn(Optional.of(review));
        when(mapper.reviewFromEntityToDto(review)).thenReturn(reviewDto);

        ReviewDto result = reviewService.deleteReviewById(100);

        assertEquals(100, result.getId());
        verify(reviewRepository, times(1)).deleteById(100);
    }

    @Test
    void testDeleteReviewById_NotFound() {
        when(reviewRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> reviewService.deleteReviewById(999));
        verify(reviewRepository, never()).deleteById(anyInt());
    }
}
