package com.example.ecommerce.service;

import com.example.ecommerce.dto.LoginResponse;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public User registerUser(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email already registered"
            );
        }

        String hashedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {

        String email = request.getEmail().trim();

        System.out.println("Login email received: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User NOT found");
                    return new ResponseStatusException(
                            HttpStatus.UNAUTHORIZED,
                            "Invalid email or password"
                    );
                });

        System.out.println("User found: " + user.getEmail());

        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        System.out.println("Password matches: " + passwordMatches);

        if (!passwordMatches) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                token
        );
    }
}