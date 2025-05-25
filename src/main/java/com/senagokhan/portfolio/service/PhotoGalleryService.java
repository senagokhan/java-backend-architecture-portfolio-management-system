package com.senagokhan.portfolio.service;

import com.senagokhan.portfolio.dto.request.PhotoUploadRequest;
import com.senagokhan.portfolio.dto.response.PhotoGallerySaveResponse;
import com.senagokhan.portfolio.entity.PhotoGallery;
import com.senagokhan.portfolio.entity.Project;
import com.senagokhan.portfolio.entity.User;
import com.senagokhan.portfolio.repository.PhotoGalleryRepository;
import com.senagokhan.portfolio.repository.ProjectRepository;
import com.senagokhan.portfolio.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoGalleryService {
    private final PhotoGalleryRepository photoGalleryRepository;

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;


    public PhotoGalleryService(PhotoGalleryRepository photoGalleryRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.photoGalleryRepository = photoGalleryRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    public PhotoGallerySaveResponse uploadPhoto(PhotoUploadRequest request) {
        try {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            User user = userRepository.findById(request.getUploadedById())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            PhotoGallery photo = new PhotoGallery();
            photo.setProject(project);
            photo.setUploadedBy(user);
            photo.setPhotoUrl(request.getPhotoUrl());

            photoGalleryRepository.save(photo);

            return new PhotoGallerySaveResponse(project.getId(), request.getPhotoUrl());

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Error uploading photo: " + e.getMessage(), e);
        }
    }

}
