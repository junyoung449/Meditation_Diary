package com.project.mt.member.service;

import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.member.domain.Member;
import com.project.mt.member.dto.response.MemberResponseDto;
import com.project.mt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    public MemberResponseDto findMemberByMemberIdx(Long memberIdx) {
        Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        return new MemberResponseDto(member.getMemberIdx(), member.getEmail(), member.getName(), member.getRefreshToken(), member.getOAuthProvider());
    }

    public boolean logoutByMemberIdx(Long memberIdx) {
        try {
            memberRepository.logoutByMemberIdx(memberIdx);
        } catch (Exception e) {
            throw new RestApiException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return true;
    }

    public boolean deleteMemberByMemberIdx(Long memberIdx) {
        try {
            memberRepository.deleteById(memberIdx);
        } catch (Exception e) {
            throw new RestApiException(ErrorCode.MEMBER_NOT_FOUND);
        }

        return true;
    }
}
