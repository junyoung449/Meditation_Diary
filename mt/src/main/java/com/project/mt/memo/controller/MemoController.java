package com.project.mt.memo.controller;

import com.project.mt.memo.domain.Memo;
import com.project.mt.memo.dto.request.MemoRequestDto;
import com.project.mt.memo.dto.response.MemoResponseDto;
import com.project.mt.memo.repository.MemoRepository;
import com.project.mt.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/memo")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;
    private final MemoRepository memoRepository;

    // 메모 리스트 조회
    @GetMapping("/list/{memberIdx}")
    public ResponseEntity<?> findMemoList(@PathVariable("memberIdx") Long memberIdx) {
        List<MemoResponseDto> result = memoService.findMemberMemoList(memberIdx);
        return ResponseEntity.ok().body(result);
    }

     // 메모 상세조회
     @GetMapping("/{memoIdx}")
     public ResponseEntity<?> findMemoByMemoIdx(@PathVariable("memoIdx") Long memoIdx) {
         return ResponseEntity.ok(memoService.findMemoByMemoIdx(memoIdx));
     }

     // 메모 작성
     @PostMapping
     public ResponseEntity<?> saveMemo(@RequestBody MemoRequestDto memoRequestDto) {
         return ResponseEntity.ok(memoService.saveMemo(memoRequestDto));
     }

     // 메모 수정
     @PutMapping
     public ResponseEntity<?> modifyMemo(@RequestBody MemoRequestDto memoRequestDto) {
         return ResponseEntity.ok(memoService.modifyMemo(memoRequestDto));
     }

    // 메모 삭제
    @DeleteMapping("/{memoIdx}")
    public ResponseEntity<?> deleteMemo(@PathVariable("memoIdx") Long memoIdx) {
        Map<String, String> response = new HashMap<>();

        if (memoService.deleteMemo(memoIdx)) {
            response.put("resmsg", "메모 삭제 성공");
        } else {
            response.put("resmsg", "메모 삭제 실패");
        }

        return ResponseEntity.ok(response);
    }
}
