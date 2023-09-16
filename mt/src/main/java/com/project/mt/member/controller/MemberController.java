package com.project.mt.member.controller;

import com.project.mt.member.domain.Member;
import com.project.mt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/{memberIdx}")
    public ResponseEntity<Member> findMemberByMemberIdx(@PathVariable Long memberIdx) {

        return ResponseEntity.ok(memberService.findMemberByMemberIdx(memberIdx));
    }
}
