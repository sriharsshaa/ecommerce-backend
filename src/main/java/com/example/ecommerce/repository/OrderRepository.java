package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository
        extends JpaRepository<Order, Long> {

    // =========================================================
    // GET ORDERS OF A USER
    // =========================================================

    @Query("""
        SELECT o
        FROM Order o
        WHERE o.userId = :userId
        ORDER BY o.createdAt DESC
    """)
    List<Order> findOrdersByUserId(
            @Param("userId") Long userId
    );


    // =========================================================
    // FIND ONE ORDER BELONGING TO A USER
    // =========================================================

    Optional<Order> findByIdAndUserId(
            Long id,
            Long userId
    );


    // =========================================================
    // REVENUE BY DATE
    // =========================================================

    @Query("""
        SELECT FUNCTION('DATE', o.createdAt),
               SUM(o.totalAmount)
        FROM Order o
        WHERE o.status <> 'CANCELLED'
        GROUP BY FUNCTION('DATE', o.createdAt)
        ORDER BY FUNCTION('DATE', o.createdAt)
    """)
    List<Object[]> getRevenueByDate();


    // =========================================================
    // CHECK WHETHER USER PURCHASED PRODUCT
    // =========================================================

    @Query("""
        SELECT COUNT(oi)
        FROM OrderItem oi
        JOIN Order o ON oi.orderId = o.id
        WHERE o.userId = :userId
        AND oi.productId = :productId
        AND o.status <> 'CANCELLED'
    """)
    long countPurchasedProduct(
            @Param("userId") Long userId,
            @Param("productId") Long productId
    );
}