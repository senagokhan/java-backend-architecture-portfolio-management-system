package com.senagokhan.portfolio.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UpdateReviewRequest {
    private Long reviewId;
    private int rating;
    private String comment;
}
