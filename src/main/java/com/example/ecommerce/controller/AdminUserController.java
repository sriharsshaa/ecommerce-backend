package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AdminUserDTO;
import com.example.ecommerce.service.AdminUserService;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(
            AdminUserService adminUserService) {

        this.adminUserService =
                adminUserService;
    }

    @GetMapping
    public List<AdminUserDTO> getAllUsers() {

        return adminUserService.getAllUsers();
    }

    @PutMapping("/{userId}/role")
    public AdminUserDTO updateUserRole(
            @PathVariable Long userId,
            @RequestParam String role) {

        return adminUserService.updateUserRole(
                userId,
                role
        );
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(
            @PathVariable Long userId,
            Authentication authentication) {

        String adminEmail =
                authentication.getName();

        adminUserService.deleteUser(
                userId,
                adminEmail
        );
    }
}