package com.example.ecommerce.service;

import com.example.ecommerce.entity.Review;
import com.example.ecommerce.repository.ReviewRepository;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Long userId, Long productId, Integer rating, String comment) {
        // Check if review already exists
        Review existingReview = reviewRepository.findByUserIdAndProductId(userId, productId).orElse(null);

        if (existingReview != null) {
            return existingReview;
        }

        // Add new review
        Review review = new Review(userId, productId, rating, comment, java.time.LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getReviews(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow();
        if (!review.getUserId().equals(userId)) {
            throw new RuntimeException("You can delete only your own review");
        }
        reviewRepository.delete(review);
}
    
}
