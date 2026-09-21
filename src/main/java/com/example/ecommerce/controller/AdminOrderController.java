package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AdminOrderDTO;
import com.example.ecommerce.service.AdminOrderService;

import org.springframework.http.ResponseEntity;
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


    // =========================================================
    // GET ALL ORDERS
    // =========================================================

    @GetMapping
    public ResponseEntity<List<AdminOrderDTO>> getAllOrders() {

        List<AdminOrderDTO> orders =
                adminOrderService.getAllOrders();

        return ResponseEntity.ok(orders);
    }


    // =========================================================
    // UPDATE ORDER STATUS
    // =========================================================

    @PutMapping("/{orderId}/status")
    public ResponseEntity<AdminOrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        AdminOrderDTO updatedOrder =
                adminOrderService.updateOrderStatus(
                        orderId,
                        status
                );

        return ResponseEntity.ok(updatedOrder);
    }
}