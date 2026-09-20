package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    // Add product to cart
    public CartItem addToCart(
            Long userId,
            Long productId,
            int quantity) {

        // Quantity should be at least 1
        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than 0"
            );
        }

        // Find product
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Product not found"
                                )
                        );

        // Check stock
        if (product.getStock() <= 0) {
            throw new IllegalArgumentException(
                    "Product is out of stock"
            );
        }

        var existingItem =
                cartRepository.findByUserIdAndProductId(
                        userId,
                        productId
                );

        if (existingItem.isPresent()) {

            CartItem cartItem = existingItem.get();

            int newQuantity =
                    cartItem.getQuantity() + quantity;

            // Check whether requested quantity exceeds stock
            if (newQuantity > product.getStock()) {
                throw new IllegalArgumentException(
                        "Only " + product.getStock()
                                + " items are available"
                );
            }

            cartItem.setQuantity(newQuantity);

            return cartRepository.save(cartItem);
        }

        // New product in cart
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException(
                    "Only " + product.getStock()
                            + " items are available"
            );
        }

        CartItem newItem =
                new CartItem(
                        userId,
                        productId,
                        quantity
                );

        return cartRepository.save(newItem);
    }

    // Get cart
    public List<CartItem> getCart(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Increase quantity
    public CartItem increaseQuantity(
            Long userId,
            Long productId) {

        CartItem cartItem =
                cartRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElseThrow();

        // Find product
        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Product not found"
                                )
                        );

        // Check stock before increasing
        if (cartItem.getQuantity() >= product.getStock()) {
            throw new IllegalArgumentException(
                    "Only " + product.getStock()
                            + " items are available"
            );
        }

        cartItem.setQuantity(
                cartItem.getQuantity() + 1
        );

        return cartRepository.save(cartItem);
    }

    // Decrease quantity
    public CartItem decreaseQuantity(
            Long userId,
            Long productId) {

        CartItem cartItem =
                cartRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElseThrow();

        if (cartItem.getQuantity() > 1) {

            cartItem.setQuantity(
                    cartItem.getQuantity() - 1
            );

            return cartRepository.save(cartItem);
        }

        cartRepository.delete(cartItem);

        return null;
    }

    // Remove product
    public void removeFromCart(
            Long userId,
            Long productId) {

        CartItem cartItem =
                cartRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElseThrow();

        cartRepository.delete(cartItem);
    }
}