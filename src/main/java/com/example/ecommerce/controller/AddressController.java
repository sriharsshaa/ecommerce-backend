package com.example.ecommerce.controller;

import com.example.ecommerce.entity.Address;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;
    private final UserRepository userRepository;

    public AddressController(
            AddressService addressService,
            UserRepository userRepository) {

        this.addressService = addressService;
        this.userRepository = userRepository;
    }

    // Get all addresses of the logged-in user
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        List<Address> addresses =
                addressService.getAddressesByUserId(user.getId());

        return ResponseEntity.ok(addresses);
    }

    // Add a new address
    @PostMapping
    public ResponseEntity<Address> addAddress(
            @RequestBody Address address,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Never trust userId coming from React.
        // Take the logged-in user's ID from JWT.
        address.setUserId(user.getId());

        Address savedAddress =
                addressService.addAddress(address);

        return ResponseEntity.ok(savedAddress);
    }

    // Update an address
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(
            @PathVariable Long id,
            @RequestBody Address address,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Address existingAddress =
                addressService.getAddressesByUserId(user.getId())
                        .stream()
                        .filter(item -> item.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Address not found"));

        Address updatedAddress =
                addressService.updateAddress(id, address);

        return ResponseEntity.ok(updatedAddress);
    }

    // Delete an address
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean belongsToUser =
                addressService.getAddressesByUserId(user.getId())
                        .stream()
                        .anyMatch(item -> item.getId().equals(id));

        if (!belongsToUser) {
            return ResponseEntity.status(403)
                    .body("You cannot delete this address");
        }

        addressService.deleteAddress(id);

        return ResponseEntity.ok("Address deleted successfully");
    }

    // Set default address
    @PutMapping("/{id}/default")
    public ResponseEntity<Address> setDefaultAddress(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        boolean belongsToUser =
                addressService.getAddressesByUserId(user.getId())
                        .stream()
                        .anyMatch(item -> item.getId().equals(id));

        if (!belongsToUser) {
            return ResponseEntity.status(403)
                    .build();
        }

        Address address =
                addressService.setDefaultAddress(id);

        return ResponseEntity.ok(address);
    }
}