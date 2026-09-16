package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity 
public class Review {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; 
    private Long productId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    public Review(){

    }
    public Review(Long userId, Long productId, Integer rating, String comment, LocalDateTime createdAt){
        this.userId=userId;
        this.productId=productId;
        this.rating=rating;
        this.comment=comment;
        this.createdAt=createdAt;
    }

    public Long getId(){
        return id;
    }
    public Long getUserId(){
        return userId;
    }
    public Long getProductId(){
        return productId;
    }
    public Integer getRating(){
        return rating;
    }
    public String getComment(){
        return comment;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setId(Long id){
        this.id=id;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }
    public void setProductId(Long productId){
        this.productId=productId;
    }
    public void setRating(Integer rating){
        this.rating=rating;
    }
    public void setComment(String comment){
        this.comment=comment;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }

}
