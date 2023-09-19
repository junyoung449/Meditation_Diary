package com.project.mt.member.controller;

import com.project.mt.member.domain.Member;
import com.project.mt.member.dto.response.MemberResponseDto;
import com.project.mt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


     @GetMapping("/{memberIdx}")
     public ResponseEntity<?> findMemberByMemberIdx(@PathVariable Long memberIdx) {
         MemberResponseDto memberResponseDto = memberService.findMemberByMemberIdx(memberIdx);

         return ResponseEntity.ok().body(memberResponseDto);
     }

    @DeleteMapping("/{memberIdx}")
    public ResponseEntity<Map<String, String>> deleteMemberByMemberIdx(@PathVariable Long memberIdx) {
        Map<String, String> response = new HashMap<>();

        memberService.deleteMemberByMemberIdx(memberIdx);

        response.put("resmsg", "회원 탈퇴를 성공하였습니다.");

        return ResponseEntity.ok(response);
    }
}
