package com.senagokhan.portfolio.service;

import com.senagokhan.portfolio.entity.Review;
import com.senagokhan.portfolio.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review) {
        try {
            if (review.getRating() < 1 || review.getRating() > 5) {
                throw new RuntimeException("Rating must be between 1 and 5");
            }
            return reviewRepository.save(review);

        } catch (Exception e) {
            throw new RuntimeException("Error occurred while adding the review: " + e.getMessage(), e);
        }
    }
}
