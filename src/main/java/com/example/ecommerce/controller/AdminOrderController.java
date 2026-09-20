package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AdminOrderDTO;
import com.example.ecommerce.service.AdminOrderService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(
            AdminOrderService adminOrderService) {

        this.adminOrderService =
                adminOrderService;
    }

    @GetMapping
    public List<AdminOrderDTO> getAllOrders() {

        return adminOrderService.getAllOrders();
    }

    @PutMapping("/{orderId}/status")
    public AdminOrderDTO updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return adminOrderService.updateOrderStatus(
                orderId,
                status
        );
    }
}