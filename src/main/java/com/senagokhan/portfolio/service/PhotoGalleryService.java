package com.senagokhan.portfolio.service;

import com.senagokhan.portfolio.config.ProjectConstant;
import com.senagokhan.portfolio.dto.request.PhotoUploadRequest;
import com.senagokhan.portfolio.dto.response.PhotoGalleryResponse;
import com.senagokhan.portfolio.entity.PhotoGallery;
import com.senagokhan.portfolio.entity.Project;
import com.senagokhan.portfolio.entity.User;
import com.senagokhan.portfolio.repository.PhotoGalleryRepository;
import com.senagokhan.portfolio.repository.ProjectRepository;
import com.senagokhan.portfolio.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public PhotoGalleryResponse uploadPhoto(PhotoUploadRequest request) {
        try {
            Project project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException(ProjectConstant.project_not_found));

            User user = userRepository.findById(request.getUploadedById())
                    .orElseThrow(() -> new RuntimeException(ProjectConstant.user_not_found));

            PhotoGallery photo = new PhotoGallery();
            photo.setProject(project);
            photo.setUploadedBy(user);
            photo.setPhotoUrl(request.getPhotoUrl());

            photoGalleryRepository.save(photo);

            return new PhotoGalleryResponse(project.getId(), request.getPhotoUrl());

        } catch (Exception e) {
            System.err.println(ProjectConstant.an_unexpected_error + e.getMessage());
            throw new RuntimeException(ProjectConstant.error_upload_photo + e.getMessage(), e);
        }
    }

    public List<PhotoGalleryResponse> getPhotosByProjectId(Long projectId) {
        try {
            List<PhotoGallery> photos = photoGalleryRepository.findByProjectId(projectId);

            if (photos.isEmpty()) {
                throw new RuntimeException(ProjectConstant.photo_not_found_for_project);
            }

            return photos.stream()
                    .map(photo -> new PhotoGalleryResponse(
                            photo.getProject().getId(),
                            photo.getPhotoUrl()
                    ))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println(ProjectConstant.error_fetching_photo + e.getMessage());
            throw new RuntimeException(ProjectConstant.unable_to_fetch + e.getMessage(), e);
        }
    }
}
