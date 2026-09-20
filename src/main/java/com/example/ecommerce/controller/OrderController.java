package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.OrderService;

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
    // Checkout / Create Order
    // =========================================================

    @PostMapping
    public Order createOrder(
            @RequestParam String paymentMethod,
            Authentication authentication) {

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

        return orderService.createOrder(
                user.getId(),
                paymentMethod
        );
    }


    // =========================================================
    // Get All Orders of Logged-in User
    // =========================================================

    @GetMapping
    public List<Order> getOrders(
            Authentication authentication) {

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

        return orderService.getOrders(
                user.getId()
        );
    }


    // =========================================================
    // Get Items of a Particular Order
    // =========================================================

    @GetMapping("/{orderId}/items")
    public List<OrderItem> getOrderItems(
            @PathVariable Long orderId,
            Authentication authentication) {

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

        return orderService.getOrderItems(
                orderId,
                user.getId()
        );
    }

    // =========================================================
    // Update Order Status
    // =========================================================

    @PutMapping("/{orderId}/status")
    public Order updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status,
            Authentication authentication) {

        String email = authentication.getName();
        User user =
                userRepository.findByEmail(email)
                        .orElseThrow();

        return orderService.updateOrderStatus(
                orderId,
                user.getId(),
                status
        );
    }

    @PutMapping("/{orderId}/cancel")
    public Order cancelOrder(
        @PathVariable Long orderId,
        Authentication authentication) {

        String email = authentication.getName();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        return orderService.cancelOrder(
                orderId,
                user.getId()
        );
    }
}
