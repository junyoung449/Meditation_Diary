package com.project.mt.meditation.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MeditationResponseDto {
    private Long meditationIdx;
    private Long memberIdx;
    private Timestamp date;
    private List<MeditationMediaResponseDto> meditationMedia;
}
