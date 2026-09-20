package com.example.ecommerce.controller;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.CartService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(
            CartService cartService,
            UserRepository userRepository) {

        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    // Add product
    @PostMapping("/add")
    public CartItem addToCart(
            @RequestParam Long productId,
            @RequestParam(defaultValue = "1") int quantity,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return cartService.addToCart(
                user.getId(),
                productId,
                quantity
        );
    }

    // Get cart
    @GetMapping
    public List<CartItem> getCart(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return cartService.getCart(user.getId());
    }

    // Increase quantity
    @PutMapping("/{productId}/increase")
    public CartItem increaseQuantity(
            @PathVariable Long productId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return cartService.increaseQuantity(
                user.getId(),
                productId
        );
    }

    // Decrease quantity
    @PutMapping("/{productId}/decrease")
    public CartItem decreaseQuantity(
            @PathVariable Long productId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return cartService.decreaseQuantity(
                user.getId(),
                productId
        );
    }

    // Remove product
    @DeleteMapping("/{productId}")
    public void removeFromCart(
            @PathVariable Long productId,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        cartService.removeFromCart(
                user.getId(),
                productId
        );
    }
}