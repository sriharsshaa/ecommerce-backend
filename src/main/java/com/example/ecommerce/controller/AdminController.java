package com.example.ecommerce.controller;

import com.example.ecommerce.service.AdminService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Test admin access
    @GetMapping("/test")
    public String adminTest(Authentication authentication) {

        return "Welcome Admin: "
                + authentication.getName();
    }

    // Admin dashboard statistics
    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {

        return adminService.getDashboardStats();
    }
}
