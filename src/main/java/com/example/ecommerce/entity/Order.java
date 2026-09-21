package com.example.ecommerce.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long addressId;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private LocalDateTime createdAt;

    // Default constructor
    public Order() {
    }

    // Parameterized constructor
    public Order(
            Long userId,
            Long addressId,
            double totalAmount,
            String paymentMethod,
            String status) {

        this.userId = userId;
        this.addressId = addressId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }


    // Getters

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    // Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}