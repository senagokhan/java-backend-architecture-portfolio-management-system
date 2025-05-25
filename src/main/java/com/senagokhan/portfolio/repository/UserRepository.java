package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
