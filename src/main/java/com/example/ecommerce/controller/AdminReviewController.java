package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AdminReviewDTO;
import com.example.ecommerce.dto.ProductRatingDTO;
import com.example.ecommerce.service.ReviewService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/reviews")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminReviewController {

    private final ReviewService reviewService;

    public AdminReviewController(
            ReviewService reviewService) {

        this.reviewService = reviewService;
    }

    // =========================================================
    // ADMIN - GET ALL REVIEWS
    // =========================================================

    @GetMapping
    public List<AdminReviewDTO> getAllReviews() {

        return reviewService
                .getAllReviewsForAdmin();
    }

    // =========================================================
    // ADMIN - PRODUCT RATING SUMMARY
    // =========================================================

    @GetMapping("/ratings")
    public List<ProductRatingDTO> getProductRatingSummary() {

        return reviewService
                .getProductRatingSummary();
    }
}