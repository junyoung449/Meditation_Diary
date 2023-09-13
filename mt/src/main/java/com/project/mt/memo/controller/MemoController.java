package com.project.mt.memo.controller;

import com.project.mt.memo.dto.response.MemoResponseDto;
import com.project.mt.memo.service.MemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/memo")
public class MemoController {

    private MemoService memoService;

    // 메모 리스트 조회
    @GetMapping("/list/{memberIdx}")
    public ResponseEntity<?> findMemoList(@PathVariable("memberIdx") Long memberIdx) {
        List<MemoResponseDto> result = memoService.findMemberMemoList(memberIdx);
        return ResponseEntity.ok().body(result);
    }

    // 메모 상세조회

    // 메모 작성

    // 메모 수정

    // 메모 삭제
}
