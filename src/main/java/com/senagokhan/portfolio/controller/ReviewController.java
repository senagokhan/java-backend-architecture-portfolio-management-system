package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.entity.Review;
import com.senagokhan.portfolio.service.ReviewService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public String addReview(@RequestBody Review review) {
        reviewService.addReview(review);
        return "Review added!";
    }
}

