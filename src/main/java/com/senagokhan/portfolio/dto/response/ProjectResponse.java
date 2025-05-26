package com.senagokhan.portfolio.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private String name;
    private String description;
    private Set<String> tags;

    private Double averageRating;

    private LocalDateTime updatedAt;
}
