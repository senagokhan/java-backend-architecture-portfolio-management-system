package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.dto.request.UpdateReviewRequest;
import com.senagokhan.portfolio.dto.response.ReviewDto;
import com.senagokhan.portfolio.entity.Review;
import com.senagokhan.portfolio.service.ReviewService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReviewDto> addReview(@RequestBody Review review) {
        ReviewDto response = reviewService.addReview(review);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        reviewService.deleteReviewById(id);
        return ResponseEntity.ok("Review deleted!");
    }

    @GetMapping("/project/{projectId}")
    public List<ReviewDto> getReviewsByProject(@PathVariable Long projectId) {
        return reviewService.getReviewsByProjectId(projectId);
    }

    @PutMapping("/edit")
    public String editReview(@RequestHeader("userId") Long userId,
                             @RequestBody UpdateReviewRequest request) {
        reviewService.updateReview(request, userId);
        return "Review updated!";
    }
}

