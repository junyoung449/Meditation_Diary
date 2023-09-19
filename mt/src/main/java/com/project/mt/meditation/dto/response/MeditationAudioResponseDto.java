package com.project.mt.meditation.dto.response;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MeditationAudioResponseDto {
    private Long audioIdx;
    private Long meditationIdx;
    private String audioUrl;
}
