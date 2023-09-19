package com.project.mt.meditation.dto.response;

import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MeditationListResponseDto {
    private Long meditationIdx;
    private Long memberIdx;
    private Timestamp date;
    private String meditationImage;
}
