package com.example.ecommerce.controller;

import com.example.ecommerce.dto.RevenueDTO;
import com.example.ecommerce.service.AdminDashboardService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService) {

        this.adminDashboardService =
                adminDashboardService;
    }

    // =========================================
    // REVENUE OVER TIME
    // =========================================

    @GetMapping("/revenue")
    public List<RevenueDTO> getRevenueByDate() {

        return adminDashboardService
                .getRevenueByDate();
    }
}