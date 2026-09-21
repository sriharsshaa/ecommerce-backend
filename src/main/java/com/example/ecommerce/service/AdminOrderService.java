package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminOrderDTO;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    // =========================================================
    // GET ALL ORDERS
    // =========================================================

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


    // =========================================================
    // UPDATE ORDER STATUS
    // =========================================================

    public AdminOrderDTO updateOrderStatus(
            Long orderId,
            String status) {


        // -----------------------------------------------------
        // 1. Find order
        // -----------------------------------------------------

        Order order =
                orderRepository
                        .findById(orderId)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Order not found"
                                )
                        );


        // -----------------------------------------------------
        // 2. Clean status
        // -----------------------------------------------------

        String newStatus =
                status.trim().toUpperCase();


        String currentStatus =
                order.getStatus();


        // -----------------------------------------------------
        // 3. Do not allow updating cancelled orders
        // -----------------------------------------------------

        if ("CANCELLED".equals(currentStatus)) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Cancelled orders cannot be updated"
            );
        }


        // -----------------------------------------------------
        // 4. Check valid status transition
        // -----------------------------------------------------

        boolean validTransition =
                ("PLACED".equals(currentStatus)
                        && "CONFIRMED".equals(newStatus))

                ||

                ("CONFIRMED".equals(currentStatus)
                        && "SHIPPED".equals(newStatus))

                ||

                ("SHIPPED".equals(currentStatus)
                        && "DELIVERED".equals(newStatus));


        if (!validTransition) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid order status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }


        // -----------------------------------------------------
        // 5. Update status
        // -----------------------------------------------------

        order.setStatus(newStatus);


        Order updatedOrder =
                orderRepository.save(order);


        // -----------------------------------------------------
        // 6. Get customer details
        // -----------------------------------------------------

        User user =
                userRepository
                        .findById(
                                updatedOrder.getUserId()
                        )
                        .orElse(null);


        String userName = null;
        String userEmail = null;


        if (user != null) {

            userName = user.getName();
            userEmail = user.getEmail();
        }


        // -----------------------------------------------------
        // 7. Return updated DTO
        // -----------------------------------------------------

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