package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminUserDTO;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminUserService {

    private final UserRepository userRepository;

    public AdminUserService(
            UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public List<AdminUserDTO> getAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<AdminUserDTO> result =
                new ArrayList<>();

        for (User user : users) {

            AdminUserDTO dto =
                    new AdminUserDTO(
                            user.getId(),
                            user.getName(),
                            user.getEmail(),
                            user.getRole()
                    );

            result.add(dto);
        }

        return result;
    }


    public AdminUserDTO updateUserRole(
            Long userId,
            String role) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );

        if (!role.equals("USER")
                && !role.equals("ADMIN")) {

            throw new RuntimeException(
                    "Invalid role"
            );
        }

        user.setRole(role);

        User updatedUser =
                userRepository.save(user);

        return new AdminUserDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getRole()
        );
    }


    public void deleteUser(
        Long userId,
        String adminEmail) {
        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"
                                )
                        );
        if (user.getEmail().equals(adminEmail)) {

                throw new RuntimeException(
                        "You cannot delete your own account"
                );
        }

        userRepository.delete(user);
    }
}