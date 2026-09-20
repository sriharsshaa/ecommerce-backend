package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ecommerce.entity.Review;

import java.util.Optional;
import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findByProductId(Long productId);

    Optional<Review> findByUserIdAndProductId(
            Long userId,
            Long productId
    );

    @Query("""
        SELECT r.productId, AVG(r.rating), COUNT(r.id)
        FROM Review r
        GROUP BY r.productId
        ORDER BY AVG(r.rating) DESC
    """)
    List<Object[]> getProductRatingSummary();
}