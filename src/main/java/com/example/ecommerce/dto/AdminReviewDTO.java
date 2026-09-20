package com.example.ecommerce.dto;

import java.time.LocalDateTime;

public class AdminReviewDTO {

    private Long id;
    private String productName;
    private String userName;
    private String userEmail;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;

    public AdminReviewDTO() {
    }

    public AdminReviewDTO(
            Long id,
            String productName,
            String userName,
            String userEmail,
            int rating,
            String comment,
            LocalDateTime createdAt) {

        this.id = id;
        this.productName = productName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}