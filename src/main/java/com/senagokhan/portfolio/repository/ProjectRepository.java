package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
}
