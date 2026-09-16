package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce.entity.Review;

import java.util.Optional;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List <Review> findByProductId(Long productId);
    Optional<Review> findByUserIdAndProductId(
        Long userId,
        Long productId
    );
    
}
