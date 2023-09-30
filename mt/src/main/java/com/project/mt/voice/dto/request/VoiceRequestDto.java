package com.project.mt.voice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class VoiceRequestDto {
    private Long memberIdx;
    private String voiceName;
}
