package com.senagokhan.portfolio.service;

import com.senagokhan.portfolio.dto.request.ProjectRequest;
import com.senagokhan.portfolio.dto.response.ProjectResponse;
import com.senagokhan.portfolio.entity.Project;
import com.senagokhan.portfolio.entity.Review;
import com.senagokhan.portfolio.entity.User;
import com.senagokhan.portfolio.repository.ProjectRepository;
import com.senagokhan.portfolio.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }


    public ProjectResponse createProject(ProjectRequest projectRequest) {
        User admin = userRepository.findById(projectRequest.getAdminId())
                .orElseThrow(() -> new RuntimeException("User not found: " + projectRequest.getAdminId()));

        Project project = modelMapper.map(projectRequest,Project.class);
        project.setCreatedBy(admin);
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

}
