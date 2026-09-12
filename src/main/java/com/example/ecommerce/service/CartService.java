package com.example.ecommerce.service;

import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Add product to cart
    public CartItem addToCart(Long userId, Long productId) {

        var existingItem =
                cartRepository.findByUserIdAndProductId(
                        userId,
                        productId
                );

        if (existingItem.isPresent()) {

            CartItem cartItem = existingItem.get();

            cartItem.setQuantity(
                    cartItem.getQuantity() + 1
            );

            return cartRepository.save(cartItem);
        }

        CartItem newItem =
                new CartItem(
                        userId,
                        productId,
                        1
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