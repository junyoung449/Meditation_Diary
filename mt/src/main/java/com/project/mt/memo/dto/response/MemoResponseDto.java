package com.project.mt.memo.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemoResponseDto {
    Long memo_idx;
//    Long member_idx;
    String content;
    LocalDateTime date;
}
