package com.example.ecommerce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.security.core.Authentication;
import com.example.ecommerce.entity.Review;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.ReviewService;
import java.util.List;



@CrossOrigin (origins = "http://localhost:5173")
@RestController 
@RequestMapping ("/api/reviews")

public class ReviewController {
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    public ReviewController(ReviewService reviewService, UserRepository userRepository) {
        this.reviewService = reviewService;
        this.userRepository = userRepository;
    }
    
    @PostMapping ("/{productId}")
    public Review addReview(
        @PathVariable Long productId,
        @RequestParam Integer rating,
        @RequestParam String comment,
        Authentication authentication
    ) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return reviewService.addReview(user.getId(), productId, rating, comment);
    }

    @GetMapping ("/{productId}")
    public List<Review> getReviews(@PathVariable Long productId) {
        return reviewService.getReviews(productId);
    }

    @DeleteMapping ("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        reviewService.deleteReview(reviewId, user.getId());
    }

}
