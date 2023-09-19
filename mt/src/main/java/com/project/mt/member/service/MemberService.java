package com.project.mt.member.service;

import com.project.mt.member.domain.Member;
import com.project.mt.member.dto.response.MemberResponseDto;
import com.project.mt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // public MemberResponseDto findMemberByMemberIdx(Long memberIdx) {
    //     Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND, "유저 없음"));
    //
    //     return new MemberResponseDto(member.getMemberIdx(), member.getEmail(), member.getName(), member.getRefreshToken(), member.getOAuthProvider());
    // }

    public boolean deleteMemberByMemberIdx(Long memberIdx) {
        try {
            memberRepository.deleteById(memberIdx);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
