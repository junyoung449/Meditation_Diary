package com.project.mt.voice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VoiceListResponseDto {
    private Long voiceIdx;
    private String voiceName;
    private String modelId;
}
