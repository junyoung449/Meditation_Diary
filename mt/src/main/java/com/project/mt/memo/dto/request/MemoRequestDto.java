package com.project.mt.memo.dto.request;

import com.project.mt.memo.domain.Memo;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemoRequestDto {
    private Long memoIdx;
    private Long memberIdx;
    private String content;
}
