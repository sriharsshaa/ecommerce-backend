package com.example.ecommerce.service;

import com.example.ecommerce.entity.Wishlist;
import com.example.ecommerce.repository.WishlistRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public Wishlist addToWishlist(Long userId, Long productId) {

        // Check if product is already in wishlist
        Wishlist existingWishlist =
                wishlistRepository
                        .findByUserIdAndProductId(userId, productId)
                        .orElse(null);

        // Already exists
        if (existingWishlist != null) {
            return existingWishlist;
        }

        // Add new wishlist item
        Wishlist wishlist =
                new Wishlist(userId, productId);

        return wishlistRepository.save(wishlist);
    }

    public List<Wishlist> getWishlist(Long userId) {

        return wishlistRepository.findByUserId(userId);
    }

    public void removeFromWishlist(
            Long userId,
            Long productId) {

        Wishlist wishlist =
                wishlistRepository
                        .findByUserIdAndProductId(
                                userId,
                                productId
                        )
                        .orElseThrow();

        wishlistRepository.delete(wishlist);
    }
}