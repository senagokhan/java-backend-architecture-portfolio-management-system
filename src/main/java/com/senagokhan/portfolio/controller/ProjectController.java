package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.dto.request.ProjectRequest;
import com.senagokhan.portfolio.dto.response.ProjectResponse;
import com.senagokhan.portfolio.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ProjectResponse createProject(@RequestBody ProjectRequest projectRequest) {
        return projectService.createProject(projectRequest);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        List<ProjectResponse> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<ProjectResponse>> getPaginatedProjects(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        List<ProjectResponse> projects = projectService.getPaginatedProjects(offset, limit);
        return ResponseEntity.ok(projects);
    }
}
