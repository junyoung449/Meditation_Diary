package com.project.mt.authentication.domain;

import java.util.Date;

import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import org.springframework.stereotype.Component;

import com.project.mt.authentication.infra.JwtTokenProvider;

import lombok.RequiredArgsConstructor;


/**
 * Auth 토큰 생성기
 */
@Component
@RequiredArgsConstructor
public class AuthTokensGenerator {
    private static final String BEARER_TYPE = "Bearer";
//    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 10;            // 10분
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 10;   // 10일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 20;  // 20일

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthTokens generate(Long memberIdx) {
        String subject = memberIdx.toString();

        // accessToken 과 refreshToken 생성
        String accessToken = generateAccessToken(subject);
        String refreshToken = generateRefreshToken(subject);

        memberRepository.saveRefreshToken(refreshToken, memberIdx);

        return AuthTokens.of(accessToken, refreshToken, BEARER_TYPE, ACCESS_TOKEN_EXPIRE_TIME / 1000L);
    }

    public String generateAccessToken(String subject) {
        long now = (new Date()).getTime();

        Date accessTokenExpiredAt = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        return jwtTokenProvider.generate(subject, accessTokenExpiredAt);
    }

    public String generateRefreshToken(String subject) {
        long now = (new Date()).getTime();
        Date refreshTokenExpiredAt = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        return jwtTokenProvider.generate(subject, refreshTokenExpiredAt);
    }

    public Long extractMemberId(String accessToken) {
        return Long.valueOf(jwtTokenProvider.extractSubject(accessToken));
    }
}
