package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByProjectId(Long projectId);
    Optional<Review> findByIdAndUserId(Long id, Long userId);
}

