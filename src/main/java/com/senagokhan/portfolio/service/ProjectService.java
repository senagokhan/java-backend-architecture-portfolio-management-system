package com.senagokhan.portfolio.service;

import com.senagokhan.portfolio.dto.request.ProjectRequest;
import com.senagokhan.portfolio.dto.response.ProjectResponse;
import com.senagokhan.portfolio.entity.Project;
import com.senagokhan.portfolio.entity.User;
import com.senagokhan.portfolio.repository.ProjectRepository;
import com.senagokhan.portfolio.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

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
}
