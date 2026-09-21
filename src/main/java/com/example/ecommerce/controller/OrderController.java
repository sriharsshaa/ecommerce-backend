package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;


    public OrderController(
            OrderService orderService,
            UserRepository userRepository) {

        this.orderService = orderService;
        this.userRepository = userRepository;
    }


    // =========================================================
    // CREATE ORDER
    // =========================================================

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestParam String paymentMethod,
            @RequestParam Long addressId,
            Authentication authentication) {

        String email =
                authentication.getName();


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        Order order =
                orderService.createOrder(
                        user.getId(),
                        addressId,
                        paymentMethod
                );


        return ResponseEntity.ok(order);
    }


    // =========================================================
    // GET ALL ORDERS OF LOGGED-IN USER
    // =========================================================

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(
            Authentication authentication) {

        String email =
                authentication.getName();


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        List<Order> orders =
                orderService.getOrders(
                        user.getId()
                );


        return ResponseEntity.ok(orders);
    }


    // =========================================================
    // GET ORDER ITEMS
    // =========================================================

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItem>> getOrderItems(
            @PathVariable Long orderId,
            Authentication authentication) {

        String email =
                authentication.getName();


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        List<OrderItem> items =
                orderService.getOrderItems(
                        orderId,
                        user.getId()
                );


        return ResponseEntity.ok(items);
    }


    // =========================================================
    // UPDATE ORDER STATUS
    // =========================================================

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            Authentication authentication) {

        String email =
                authentication.getName();


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        Order updatedOrder =
                orderService.updateOrderStatus(
                        orderId,
                        user.getId(),
                        status
                );


        return ResponseEntity.ok(
                updatedOrder
        );
    }


    // =========================================================
    // CANCEL ORDER
    // =========================================================

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication) {

        String email =
                authentication.getName();


        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );


        Order cancelledOrder =
                orderService.cancelOrder(
                        orderId,
                        user.getId()
                );


        return ResponseEntity.ok(
                cancelledOrder
        );
    }
}