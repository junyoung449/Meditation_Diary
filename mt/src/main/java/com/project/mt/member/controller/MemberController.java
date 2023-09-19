package com.project.mt.member.controller;

import com.project.mt.member.domain.Member;
import com.project.mt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    // @GetMapping("/{memberIdx}")
    // public ResponseEntity<?> findMemberByMemberIdx(@PathVariable Long memberIdx) {
    //     return ResponseEntity.ok(memberService.findMemberByMemberIdx(memberIdx));
    // }

    @DeleteMapping("/{memberIdx}")
    public ResponseEntity<Map<String, String>> deleteMemberByMemberIdx(@PathVariable Long memberIdx) {
        Map<String, String> response = new HashMap<>();

        if (memberService.deleteMemberByMemberIdx(memberIdx)) {
            response.put("resmsg", "회원 탈퇴 성공");
        } else {
            response.put("resmsg", "회원 탈퇴 실패");
        }

        return ResponseEntity.ok(response);
    }
}
