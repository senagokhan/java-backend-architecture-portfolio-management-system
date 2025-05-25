package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.dto.request.PhotoUploadRequest;
import com.senagokhan.portfolio.dto.response.PhotoGallerySaveResponse;
import com.senagokhan.portfolio.service.PhotoGalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/photo-gallery")
public class PhotoGalleryController {

    private final PhotoGalleryService photoGalleryService;

    public PhotoGalleryController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PhotoGallerySaveResponse> uploadPhoto(@RequestBody PhotoUploadRequest request) {
        PhotoGallerySaveResponse response = photoGalleryService.uploadPhoto(request);
        return ResponseEntity.ok(response);
    }
}
