package com.senagokhan.portfolio.service;

import com.senagokhan.portfolio.dto.request.*;
import com.senagokhan.portfolio.dto.response.ProjectResponse;
import com.senagokhan.portfolio.entity.Project;
import com.senagokhan.portfolio.entity.Review;
import com.senagokhan.portfolio.entity.User;
import com.senagokhan.portfolio.entity.Tags;
import com.senagokhan.portfolio.repository.ProjectRepository;
import com.senagokhan.portfolio.repository.TagsRepository;
import com.senagokhan.portfolio.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    private final TagsRepository tagsRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, TagsRepository tagsRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.tagsRepository = tagsRepository;
        this.modelMapper = modelMapper;
    }

    public ProjectResponse createProject(ProjectRequest projectRequest) {
        User admin = userRepository.findById(projectRequest.getAdminId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + projectRequest.getAdminId()));

        Project project = modelMapper.map(projectRequest, Project.class);
        project.setCreatedBy(admin);

        if (projectRequest.getTags() != null && !projectRequest.getTags().isEmpty()) {
            Set<Tags> tags = projectRequest.getTags().stream()
                    .map(tagName -> tagsRepository.findByName(tagName)
                            .orElseThrow(() -> new RuntimeException("Tag not found with name: " + tagName)))
                    .collect(Collectors.toSet());

            project.setTags(tags);
        }

        projectRepository.save(project);
        return modelMapper.map(project, ProjectResponse.class);
    }

    public List<ProjectResponse> getAllProjects() {
        logger.info("Listing all projects...");
        Sort sort = Sort.by(Sort.Direction.ASC, "updatedAt");
        logger.info("Listing all projects...");
        List<Project> projects = projectRepository.findAll();

        if (projects.isEmpty()) {
            throw new RuntimeException("There is no project.");
        }

        List<ProjectResponse> projectResponses = projects.stream()
                .map(project -> {
                    ProjectResponse response = modelMapper.map(project, ProjectResponse.class);
                    double avg = project.getReviews().stream()
                            .mapToInt(Review::getRating)
                            .average()
                            .orElse(0.0);
                    response.setAverageRating(avg);
                    return response;
                })
                .collect(Collectors.toList());

        logger.info("{} projects found.", projectResponses.size());
        return projectResponses;
    }

    public List<ProjectResponse> getPaginatedProjects(int offset, int limit) {
        Pageable pageable = PageRequest.of(offset, limit);
        Page<Project> page = projectRepository.findAll(pageable);
        return page.stream()
                .map(project -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
    }

    public void deleteProjectById(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Project with ID " + projectId + " not found.");
        }
        projectRepository.deleteById(projectId);
    }

    public ProjectResponse updateProject(ProjectUpdateRequest updateRequest) {
        logger.info("The project update request has been received: ID {}", updateRequest.getId());

        Project project = projectRepository.findById(updateRequest.getId())
                .orElseThrow(() -> {
                    logger.error("Project not found: ID {}", updateRequest.getId());
                    return new RuntimeException("Project not found: " + updateRequest.getId());
                });

        if (updateRequest.getName() != null) {
            project.setName(updateRequest.getName());
        }
        if (updateRequest.getDescription() != null) {
            project.setDescription(updateRequest.getDescription());
        }

        Project updatedProject = projectRepository.save(project);
        logger.info("Project updated successfully: ID {}", updatedProject.getId());

        ProjectResponse response = modelMapper.map(updatedProject, ProjectResponse.class);
        response.setTags(
                updatedProject.getTags().stream()
                        .map(Tags::getName)
                        .collect(Collectors.toSet())
        );

        return response;
    }

    public List<ProjectResponse> searchProjects(String name, String description) {
        try {
            if ((name == null || name.isEmpty()) && (description == null || description.isEmpty())) {
                throw new RuntimeException("Error, name and description is null or empty.");
            }
            List<Project> projectList = projectRepository
                    .findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, description);

            return projectList.stream().map(project -> modelMapper.map(project, ProjectResponse.class)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error while searching projects: " + e.getMessage(), e);
        }
    }

    public Page<ProjectResponse> getSortedProjects(String direction, int offset, int limit) {
        try {
            Pageable pageable = PageRequest.of(offset, limit, Sort.by(Sort.Direction.fromString(direction), "name"));
            Page<Project> page = projectRepository.findAll(pageable);

            return page.map(project -> modelMapper.map(project, ProjectResponse.class));
        } catch (Exception e) {
            throw new RuntimeException("Error while sorting projects: " + e.getMessage(), e);
        }
    }

    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found: " + projectId));

        ProjectResponse response = modelMapper.map(project, ProjectResponse.class);

        response.setTags(
                project.getTags().stream()
                        .map(Tags::getName)
                        .collect(Collectors.toSet())
        );

        return response;
    }

    public ProjectResponse addTagsToProject(AddTagsToProjectRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found: " + request.getProjectId()));

        Set<Tags> existingTags = project.getTags();

        Set<String> existingTagNames = existingTags.stream()
                .map(Tags::getName)
                .collect(Collectors.toSet());

        for (String tag : request.getTags()) {
            if (existingTagNames.contains(tag)) {
                throw new RuntimeException("Tag already exists for this project: " + tag);
            }
        }

        Set<Tags> newTags = request.getTags().stream()
                .map(tagName -> tagsRepository.findByName(tagName)
                        .orElseThrow(() -> new RuntimeException("Tag not found: " + tagName)))
                .collect(Collectors.toSet());

        existingTags.addAll(newTags);
        project.setTags(existingTags);

        Project updatedProject = projectRepository.save(project);

        ProjectResponse response = modelMapper.map(updatedProject, ProjectResponse.class);
        response.setTags(
                updatedProject.getTags().stream()
                        .map(Tags::getName)
                        .collect(Collectors.toSet())
        );

        return response;
    }


    public Page<ProjectResponse> getProjectsSortedBy(String sortBy, String sortOrder, int offset, int limit) {
        try {
            Sort.Direction direction = Sort.Direction.ASC;
            if ("desc".equalsIgnoreCase(sortOrder)) {
                direction = Sort.Direction.DESC;
            }

            Pageable pageable = PageRequest.of(offset, limit, Sort.by(direction, sortBy));
            Page<Project> page = projectRepository.findAll(pageable);
            return page.map(project -> modelMapper.map(project, ProjectResponse.class));
        } catch (Exception e) {
            throw new RuntimeException("Error while sorting projects: " + e.getMessage(), e);
        }
    }
    private Double calculateAverageRating(Project project) {
        List<Review> reviews = project.getReviews();
        if (reviews == null || reviews.isEmpty()) return 0.0;

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    public List<ProjectResponse> sortProjectsByAverageRating(String sortOrder) {
        try {
            List<Project> projects = projectRepository.findAll();
            List<ProjectResponse> averageRatingOfProject = projects.stream()
                    .sorted((project1, project2) -> {
                        Double avg1 = calculateAverageRating(project1);
                        Double avg2 = calculateAverageRating(project2);

                        int comparison;
                        if ("desc".equalsIgnoreCase(sortOrder)) {
                            comparison = Double.compare(avg2, avg1);
                        } else {
                            comparison = Double.compare(avg1, avg2);
                        }
                        return comparison;
                    })
                    .map(project -> {
                        ProjectResponse response = modelMapper.map(project, ProjectResponse.class);
                        response.setAverageRating(calculateAverageRating(project));
                        return response;
                    })
                    .collect(Collectors.toList());
            return averageRatingOfProject;

        } catch (Exception e) {
            throw new RuntimeException("Error while sorting projects by average rating: " + e.getMessage(), e);
        }
    }

    public List<ProjectResponse> filterProjectsByTag(String tagName) {
        List<Project> projects = projectRepository.findByTagName(tagName);
        return projects.stream()
                .map(project -> {
                    ProjectResponse response = modelMapper.map(project, ProjectResponse.class);
                    response.setTags(
                            project.getTags().stream()
                                    .map(Tags::getName)
                                    .collect(Collectors.toSet())
                    );
                    return response;
                })
                .collect(Collectors.toList());
    }

    public ProjectResponse removeTagFromProject(RemoveTagRequest request) {
        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found: " + request.getProjectId()));

        Tags tagToRemove = tagsRepository.findByName(request.getTagName())
                .orElseThrow(() -> new RuntimeException("Tag not found: " + request.getTagName()));

        boolean removed = project.getTags().remove(tagToRemove);
        if (!removed) {
            throw new RuntimeException("The project does not have this tag: " + request.getTagName());
        }

        Project updatedProject = projectRepository.save(project);

        ProjectResponse response = modelMapper.map(updatedProject, ProjectResponse.class);
        response.setTags(
                updatedProject.getTags().stream()
                        .map(Tags::getName)
                        .collect(Collectors.toSet())
        );

        return response;
    }

    public List<Project> getProjectsByTags(List<String> tagNames) {
        Long tagCount = (long) tagNames.size();
        return projectRepository.findProjectsByTags(tagNames, tagCount);
    }

    public Tags editTag(EditTagRequest request) {
        Tags tag = tagsRepository.findById(request.getTagId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag not found: " + request.getTagId()));

        String newTagName = request.getNewTagName();
        if (newTagName == null || newTagName.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The new tag name is empty or invalid.");
        }

        tag.setName(newTagName);
        return tagsRepository.save(tag);
    }

}

