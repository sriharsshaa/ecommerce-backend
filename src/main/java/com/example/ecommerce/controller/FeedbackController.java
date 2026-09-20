package com.example.ecommerce.controller;

import com.example.ecommerce.dto.AdminFeedbackDTO;
import com.example.ecommerce.entity.Feedback;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.FeedbackService;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "http://localhost:5173")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final UserRepository userRepository;

    public FeedbackController(
            FeedbackService feedbackService,
            UserRepository userRepository) {

        this.feedbackService =
                feedbackService;

        this.userRepository =
                userRepository;
    }

    @PostMapping
    public Feedback submitFeedback(
            @RequestParam int rating,
            @RequestParam String comment,
            Authentication authentication) {

        String email =
                authentication.getName();

        User user =
                userRepository
                        .findByEmail(email)
                        .orElseThrow();

        return feedbackService.saveFeedback(
                user.getId(),
                rating,
                comment
        );
    }

    @GetMapping
    public List<AdminFeedbackDTO> getAllFeedback() {

        return feedbackService.getAllFeedback();
    }
}