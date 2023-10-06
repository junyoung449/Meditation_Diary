package com.project.mt.memo.dto.response;

import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemoResponseDto {
    private Long memoIdx;
    private Long memberIdx;
    private String content;
    private Timestamp date;
}
