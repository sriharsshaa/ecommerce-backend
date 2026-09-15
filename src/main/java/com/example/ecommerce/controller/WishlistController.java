package com.example.ecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.entity.Wishlist;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.WishlistService;
import java.util.List;

@CrossOrigin (origins ="http://localhost:5173")
@RestController
@RequestMapping ("/api/wishlist")

public class WishlistController {
    private final WishlistService wishlistService;
    private final UserRepository userRepository;

    public WishlistController (WishlistService wishlistService, UserRepository userRepository){
        this.wishlistService= wishlistService;
        this.userRepository = userRepository;
    }

     // Add to wishlist
    @PostMapping ("/{productId}") 
    public Wishlist addToWishlist(
        @PathVariable Long productId, Authentication authentication){
        String email = authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow();
        return wishlistService.addToWishlist(user.getId(),productId);
    }

    //Get Wishlist
    @GetMapping 
    public List<Wishlist> getWishlist(Authentication authentication){
        String email = authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow();
        return wishlistService.getWishlist(user.getId());
    }

    //Remove from Wishlist 
    @DeleteMapping ("/{productId}")
    public void removeFromWishlist(
        @PathVariable Long productId, Authentication authentication){
            String email=authentication.getName();
            User user= userRepository.findByEmail(email).orElseThrow();
            wishlistService.removeFromWishlist(user.getId(),productId);
    }
}
