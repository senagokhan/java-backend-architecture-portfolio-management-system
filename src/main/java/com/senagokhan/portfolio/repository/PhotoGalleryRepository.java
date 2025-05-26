package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.PhotoGallery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery,Long> {
    List<PhotoGallery> findByProjectId(Long projectId);
}
