package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.service.PhotoGalleryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photo-gallery")
public class PhotoGallery {

    private final PhotoGalleryService photoGalleryService;

    public PhotoGallery(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }
}
