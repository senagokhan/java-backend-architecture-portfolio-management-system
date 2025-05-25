package com.senagokhan.portfolio.repository;

import com.senagokhan.portfolio.entity.PhotoGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoGalleryRepository extends JpaRepository<PhotoGallery,Long> {
}
