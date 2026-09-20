package com.example.ecommerce.service;

import com.example.ecommerce.entity.Order;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public AdminService(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Map<String, Object> getDashboardStats() {

        // Total users
        long totalUsers =
                userRepository.count();

        // Total products
        long totalProducts =
                productRepository.count();

        // Total orders
        long totalOrders =
                orderRepository.count();

        // Get all orders
        List<Order> orders =
                orderRepository.findAll();

        // Calculate total revenue
        double totalRevenue = 0;

        for (Order order : orders) {
            totalRevenue += order.getTotalAmount();
        }

        // Create dashboard response
        Map<String, Object> dashboard =
                new HashMap<>();

        dashboard.put(
                "totalUsers",
                totalUsers
        );

        dashboard.put(
                "totalProducts",
                totalProducts
        );

        dashboard.put(
                "totalOrders",
                totalOrders
        );

        dashboard.put(
                "totalRevenue",
                totalRevenue
        );

        return dashboard;
    }
}
