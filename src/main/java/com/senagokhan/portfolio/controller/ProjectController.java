package com.senagokhan.portfolio.controller;

import com.senagokhan.portfolio.dto.request.*;
import com.senagokhan.portfolio.dto.response.ProjectResponse;
import com.senagokhan.portfolio.entity.Project;
import com.senagokhan.portfolio.entity.Tags;
import com.senagokhan.portfolio.service.ProjectService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
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
        log.info("Returning {} projects with average ratings.", projects.size());
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<ProjectResponse>> getPaginatedProjects(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        List<ProjectResponse> projects = projectService.getPaginatedProjects(offset, limit);
        return ResponseEntity.ok(projects);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProjectById(projectId);
        return ResponseEntity.ok("Project deleted successfully.");
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectResponse>> searchProjects(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description
    ) {
        List<ProjectResponse> projects = projectService.searchProjects(name, description);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectResponse> updateProject(@Valid @RequestBody ProjectUpdateRequest updateRequest) {
        ProjectResponse updatedProject = projectService.updateProject(updateRequest);
        return ResponseEntity.ok(updatedProject);
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<ProjectResponse>> getSortedProjects(
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit)
    {
        Page<ProjectResponse> projects = projectService.getSortedProjects(direction,offset,limit);
        return ResponseEntity.ok(projects.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        ProjectResponse project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }

    @PatchMapping("/add-tags")
    public ResponseEntity<ProjectResponse> addTagsToProject(@RequestBody AddTagsToProjectRequest request) {
        ProjectResponse response = projectService.addTagsToProject(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/sorted-by")
    public ResponseEntity<List<ProjectResponse>> getProjectSortedBy(
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortOrder,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "10") int limit) {

        Page<ProjectResponse> sortedProjects = projectService.getProjectsSortedBy(sortBy, sortOrder, offset, limit);
        return ResponseEntity.ok(sortedProjects.getContent());
    }

    @GetMapping("/sorted-by-rating")
    public ResponseEntity<List<ProjectResponse>> getProjectsSortedByRating(
            @RequestParam(defaultValue = "asc") String sortOrder) {

        List<ProjectResponse> sortedProjects = projectService.sortProjectsByAverageRating(sortOrder);
        return ResponseEntity.ok(sortedProjects);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProjectResponse>> filterProjectsByTag(@RequestParam String tag) {
        List<ProjectResponse> projects = projectService.filterProjectsByTag(tag);
        return ResponseEntity.ok(projects);
    }

    @PatchMapping("/remove-tag")
    public ResponseEntity<ProjectResponse> removeTagFromProject(@RequestBody @Valid RemoveTagRequest request) {
        ProjectResponse response = projectService.removeTagFromProject(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filter-by-tags")
    public List<Project> getProjectsByTags(@RequestParam List<String> tags) {
        return projectService.getProjectsByTags(tags);
    }

    @PatchMapping("/edit-tag")
    public ResponseEntity<Tags> editTag(@RequestBody @Valid EditTagRequest request) {
        Tags updatedTag = projectService.editTag(request);
        return ResponseEntity.ok(updatedTag);
    }
}


