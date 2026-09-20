package com.example.ecommerce.service;

import com.example.ecommerce.dto.AdminFeedbackDTO;
import com.example.ecommerce.entity.Feedback;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.FeedbackRepository;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;

    public FeedbackService(
            FeedbackRepository feedbackRepository,
            UserRepository userRepository) {

        this.feedbackRepository =
                feedbackRepository;

        this.userRepository =
                userRepository;
    }

    public Feedback saveFeedback(
            Long userId,
            int rating,
            String comment) {

        Feedback feedback =
                new Feedback(
                        userId,
                        rating,
                        comment
                );

        return feedbackRepository.save(
                feedback
        );
    }

    public List<AdminFeedbackDTO> getAllFeedback() {

        List<Feedback> feedbackList =
                feedbackRepository.findAll();

        List<AdminFeedbackDTO> result =
                new ArrayList<>();

        for (Feedback feedback : feedbackList) {

            User user =
                    userRepository
                            .findById(
                                    feedback.getUserId()
                            )
                            .orElse(null);

            String userName =
                    user != null
                            ? user.getName()
                            : "Unknown User";

            String userEmail =
                    user != null
                            ? user.getEmail()
                            : "Unknown Email";

            result.add(
                    new AdminFeedbackDTO(
                            feedback.getId(),
                            feedback.getUserId(),
                            userName,
                            userEmail,
                            feedback.getRating(),
                            feedback.getComment(),
                            feedback.getCreatedAt()
                    )
            );
        }

        return result;
    }
}