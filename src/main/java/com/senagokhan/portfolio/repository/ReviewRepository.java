package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {
}
