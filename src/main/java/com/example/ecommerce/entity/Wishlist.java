package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Wishlist {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId; 
    private Long productId;
    
    public Wishlist(){

    }
    public Wishlist(Long userId, Long productId){
        this.userId=userId;
        this.productId=productId;
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

    public void setId(Long id){
        this.id=id;
    }
    public void setUserId(Long userId){
        this.userId=userId;
    }
    public void setProductId(Long productId){
        this.productId=productId;
    }
    
}
