package com.project.mt.authentication.application;

import org.springframework.stereotype.Service;

import com.project.mt.authentication.domain.AuthTokens;
import com.project.mt.authentication.domain.AuthTokensGenerator;
import com.project.mt.authentication.domain.oauth.OAuthInfoResponse;
import com.project.mt.authentication.domain.oauth.OAuthLoginParams;
import com.project.mt.authentication.domain.oauth.RequestOAuthInfoService;
import com.project.mt.authentication.dto.AuthResponseDto;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;

    // 각 서비스 소셜의 서버에 accessToken, refreshToken 요청을 담당
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthResponseDto login(OAuthLoginParams params) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        Long memberIdx = findOrCreateMember(oAuthInfoResponse);

        return new AuthResponseDto(memberIdx, authTokensGenerator.generate(memberIdx).getAccessToken());
    }

    /**
     * DB에 oAuthInfoResponse 와 일치하는 유저 정보가
     * 이미 있으면 유저의 고유 idx 를 가져오고,
     * 없으면 DB에 유저를 저장
     * @param oAuthInfoResponse : DB에 있는지 확인할 유저의 정보
     * @return
     */
    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        System.out.println(oAuthInfoResponse.getOAuthProvider());
        return memberRepository.findMemberByEmail(oAuthInfoResponse.getEmail())
                .map(Member::getMemberIdx)
                .orElseGet(() -> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .email(oAuthInfoResponse.getEmail())
                .name(oAuthInfoResponse.getNickname())
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return memberRepository.save(member).getMemberIdx();
    }
}
