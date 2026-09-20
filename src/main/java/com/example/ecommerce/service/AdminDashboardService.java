package com.example.ecommerce.service;

import com.example.ecommerce.dto.RevenueDTO;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminDashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public AdminDashboardService(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public long getTotalUsers() {
        return userRepository.count();
    }

    public long getTotalProducts() {
        return productRepository.count();
    }

    public long getTotalOrders() {
        return orderRepository.count();
    }

    public double getTotalRevenue() {

        List<Order> orders =
                orderRepository.findAll();

        double totalRevenue = 0;

        for (Order order : orders) {

            if (!"CANCELLED".equals(order.getStatus())) {
                totalRevenue += order.getTotalAmount();
            }
        }

        return totalRevenue;
    }

    public List<RevenueDTO> getRevenueByDate() {

        List<Object[]> rows =
                orderRepository.getRevenueByDate();

        List<RevenueDTO> result =
                new ArrayList<>();

        for (Object[] row : rows) {

            String date =
                    row[0].toString();

            double revenue =
                    ((Number) row[1]).doubleValue();

            result.add(
                    new RevenueDTO(
                            date,
                            revenue
                    )
            );
        }

        return result;
    }
}