package com.project.mt.authentication.application;

import org.springframework.stereotype.Service;

import com.project.mt.authentication.domain.AuthTokens;
import com.project.mt.authentication.domain.AuthTokensGenerator;
import com.project.mt.authentication.domain.oauth.OAuthInfoResponse;
import com.project.mt.authentication.domain.oauth.OAuthLoginParams;
import com.project.mt.authentication.domain.oauth.RequestOAuthInfoService;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberId = findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findMemberByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getMemberIdx)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                // .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getMemberIdx();
    }
}
