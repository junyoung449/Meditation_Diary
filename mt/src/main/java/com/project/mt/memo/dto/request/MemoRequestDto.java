package com.project.mt.memo.dto.request;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemoRequestDto {
    Long memo_idx;
}
