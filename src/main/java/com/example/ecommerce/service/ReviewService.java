package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminReviewDTO;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.Review;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.ReviewRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.dto.ProductRatingDTO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {

        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // =========================================================
    // ADD REVIEW
    // =========================================================

    public Review addReview(
            Long userId,
            Long productId,
            Integer rating,
            String comment) {

        // Check if review already exists
        Review existingReview =
                reviewRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElse(null);

        if (existingReview != null) {
            return existingReview;
        }

        Review review =
                new Review(
                        userId,
                        productId,
                        rating,
                        comment,
                        java.time.LocalDateTime.now()
                );

        return reviewRepository.save(review);
    }

    // =========================================================
    // GET REVIEWS FOR PRODUCT
    // =========================================================

    public List<Review> getReviews(Long productId) {

        return reviewRepository
                .findByProductId(productId);
    }

    // =========================================================
    // DELETE REVIEW
    // =========================================================

    public void deleteReview(
            Long reviewId,
            Long userId) {

        Review review =
                reviewRepository
                        .findById(reviewId)
                        .orElseThrow();

        if (!review.getUserId().equals(userId)) {

            throw new RuntimeException(
                    "You can delete only your own review"
            );
        }

        reviewRepository.delete(review);
    }

    // =========================================================
    // ADMIN - GET ALL REVIEWS
    // =========================================================

    public List<AdminReviewDTO> getAllReviewsForAdmin() {

        List<Review> reviews =
                reviewRepository.findAll();

        List<AdminReviewDTO> result =
                new ArrayList<>();

        for (Review review : reviews) {

            Product product =
                    productRepository
                            .findById(
                                    review.getProductId()
                            )
                            .orElse(null);

            User user =
                    userRepository
                            .findById(
                                    review.getUserId()
                            )
                            .orElse(null);

            String productName =
                    product != null
                            ? product.getName()
                            : "Unknown Product";

            String userName =
                    user != null
                            ? user.getName()
                            : "Unknown User";

            String userEmail =
                    user != null
                            ? user.getEmail()
                            : "Unknown Email";

            result.add(
                    new AdminReviewDTO(
                            review.getId(),
                            productName,
                            userName,
                            userEmail,
                            review.getRating(),
                            review.getComment(),
                            review.getCreatedAt()
                    )
            );
        }

        return result;
    }

    public List<ProductRatingDTO> getProductRatingSummary() {

    List<Object[]> rows =
            reviewRepository.getProductRatingSummary();

    List<ProductRatingDTO> result =
            new ArrayList<>();

    for (Object[] row : rows) {

        Long productId =
                ((Number) row[0]).longValue();

        double averageRating =
                ((Number) row[1]).doubleValue();

        long reviewCount =
                ((Number) row[2]).longValue();

        Product product =
                productRepository
                        .findById(productId)
                        .orElse(null);

        if (product != null) {

            result.add(
                    new ProductRatingDTO(
                            product.getName(),
                            Math.round(averageRating * 10.0) / 10.0,
                            reviewCount
                    )
            );
        }
    }

    return result;
}
    
}