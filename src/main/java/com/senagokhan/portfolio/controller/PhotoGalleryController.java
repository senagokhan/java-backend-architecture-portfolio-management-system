package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.dto.request.PhotoUploadRequest;
import com.senagokhan.portfolio.dto.response.PhotoGalleryResponse;
import com.senagokhan.portfolio.service.PhotoGalleryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/photo-gallery")
public class PhotoGalleryController {

    private final PhotoGalleryService photoGalleryService;

    public PhotoGalleryController(PhotoGalleryService photoGalleryService) {
        this.photoGalleryService = photoGalleryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PhotoGalleryResponse> uploadPhoto(@RequestBody PhotoUploadRequest request) {
        PhotoGalleryResponse response = photoGalleryService.uploadPhoto(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<PhotoGalleryResponse>> getPhotosByProjectId(@PathVariable Long projectId) {
        List<PhotoGalleryResponse> photos = photoGalleryService.getPhotosByProjectId(projectId);
        return ResponseEntity.ok(photos);
    }

    @DeleteMapping("/delete-photo/{id}")
    public ResponseEntity<String> deletePhotoById(@PathVariable Long id) {
        photoGalleryService.deletePhotoById(id);
        return ResponseEntity.ok("Photo deleted successfully.");
    }

}
