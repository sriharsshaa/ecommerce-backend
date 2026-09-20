package com.example.ecommerce.dto;

public class ProductRatingDTO {

    private String productName;
    private double averageRating;
    private long reviewCount;

    public ProductRatingDTO() {
    }

    public ProductRatingDTO(
            String productName,
            double averageRating,
            long reviewCount) {

        this.productName = productName;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }
}