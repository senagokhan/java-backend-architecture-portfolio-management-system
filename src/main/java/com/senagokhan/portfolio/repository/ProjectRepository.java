package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    Page<Project> findAll(Pageable pageable);
    @Query("SELECT p FROM Project p JOIN p.tags t WHERE t.name = :tagName")
    List<Project> findByTagName(@Param("tagName") String tagName);
}
