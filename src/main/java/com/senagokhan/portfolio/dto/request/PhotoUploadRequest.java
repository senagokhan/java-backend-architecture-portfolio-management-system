package com.senagokhan.portfolio.dto.request;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class PhotoUploadRequest {
    private Long projectId;
    private String photoUrl;
    private Long uploadedById;
}
