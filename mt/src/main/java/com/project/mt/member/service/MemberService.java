package com.project.mt.member.service;

import com.project.mt.error.controller.GlobalExceptionHandler;
import com.project.mt.error.dto.BusinessException;
import com.project.mt.error.dto.ErrorCode;
import com.project.mt.error.dto.response.ErrorResponse;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final GlobalExceptionHandler globalExceptionHandler;

    public Member findMemberByMemberIdx(Long memberIdx) {

        Optional<Member> member = memberRepository.findMemberByMemberIdx(memberIdx);

        return memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND, "유저 없음"));
    }
}
