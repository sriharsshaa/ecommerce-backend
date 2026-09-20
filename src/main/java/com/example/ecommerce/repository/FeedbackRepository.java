package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository
        extends JpaRepository<Feedback, Long> {

}