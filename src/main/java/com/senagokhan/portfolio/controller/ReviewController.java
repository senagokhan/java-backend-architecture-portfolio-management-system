package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.dto.response.ReviewDto;
import com.senagokhan.portfolio.entity.Review;
import com.senagokhan.portfolio.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/project/{projectId}")
    public List<ReviewDto> getReviewsByProject(@PathVariable Long projectId) {
        return reviewService.getReviewsByProjectId(projectId);
    }
}

