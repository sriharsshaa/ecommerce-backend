package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminOrderDTO;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final AdminService adminService;


    public AdminOrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            AdminService adminService) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.adminService = adminService;
    }

    public List<AdminOrderDTO> getAllOrders() {

        List<Order> orders =
                orderRepository.findAll();
        List<AdminOrderDTO> result =
                new ArrayList<>();

        for (Order order : orders) {

            User user =
                    userRepository
                            .findById(order.getUserId())
                            .orElse(null);

            String userName = null;
            String userEmail = null;

            if (user != null) {
                userName = user.getName();
                userEmail = user.getEmail();
            }

            AdminOrderDTO dto =
                    new AdminOrderDTO(
                            order.getId(),
                            order.getUserId(),
                            userName,
                            userEmail,
                            order.getTotalAmount(),
                            order.getPaymentMethod(),
                            order.getStatus(),
                            order.getCreatedAt()
                    );

            result.add(dto);
        }

        return result;
    }

public AdminOrderDTO updateOrderStatus(
        Long orderId,
        String status) {

    Order order =
            orderRepository.findById(orderId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Order not found"
                            )
                    );
    String currentStatus =
            order.getStatus();
    boolean validTransition =
            (currentStatus.equals("PLACED")
                    && status.equals("CONFIRMED"))
            ||
            (currentStatus.equals("CONFIRMED")
                    && status.equals("SHIPPED"))
            ||
            (currentStatus.equals("SHIPPED")
                    && status.equals("DELIVERED"));

    if (!validTransition) {
        throw new RuntimeException(
                "Invalid order status transition: "
                        + currentStatus
                        + " -> "
                        + status
        );
    }

    order.setStatus(status);

    Order updatedOrder =
            orderRepository.save(order);
    User user =
            userRepository
                    .findById(updatedOrder.getUserId())
                    .orElse(null);

    String userName = null;
    String userEmail = null;

    if (user != null) {
        userName = user.getName();
        userEmail = user.getEmail();
    }

    return new AdminOrderDTO(
            updatedOrder.getId(),
            updatedOrder.getUserId(),
            userName,
            userEmail,
            updatedOrder.getTotalAmount(),
            updatedOrder.getPaymentMethod(),
            updatedOrder.getStatus(),
            updatedOrder.getCreatedAt()
    );
}

}