package com.project.mt.meditation.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MeditationImageResponseDto {
    private Long imageIdx;
    private Long meditationIdx;
    private String imageUrl;
}
