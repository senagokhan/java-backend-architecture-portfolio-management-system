package com.senagokhan.portfolio.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    @NotBlank(message = "Project name must not be empty")
    @Size(max = 100, message = "Project name must be at most 100 characters")
    private String name;

    @NotBlank(message = "Description must not be empty")
    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    @NotNull(message = "Admin ID is required")
    private Long adminId;
}
