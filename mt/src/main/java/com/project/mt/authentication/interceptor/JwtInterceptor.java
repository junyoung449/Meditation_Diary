package com.project.mt.authentication.interceptor;

import com.project.mt.authentication.domain.AuthTokensGenerator;
import com.project.mt.authentication.infra.JwtTokenProvider;
import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import com.project.mt.member.domain.Member;
import com.project.mt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;

    /**
     * Http 요청이 들어온 경우, 가장 처음 만나는 메서드
     * 여기서 AccessToken 이 유효한지, 유효하지 않다면 RefreshToken 은 유효한지, 검증해야 함
     *
     * 1) AccessToken 이 유효한 경우 : 그냥 return true
     * 2) AccessToken 이 유효하지 않아서 RefreshToken 이 유효한지 검증했는데 유효한 경우 : 다시 AccessToken 발급해주고 그거 리턴
     * 3) AccessToken, RefreshToken 둘 다 유효하지 않은 경우
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception, RestApiException {
        // React와의 연동으로 인한 CORS 정책 판단 조건
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // 인가 요청 여부확인
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // JWT 여부 확인
        String accessToken = "";
        try {
            accessToken = authorization.replaceAll("Bearer ", "");
        } catch (NullPointerException e) {
            throw new RestApiException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        Long memberIdx = authTokensGenerator.extractMemberId(accessToken);
        Member member = memberRepository.findMemberByMemberIdx(memberIdx).orElseThrow(() -> new RestApiException(ErrorCode.MEMBER_NOT_FOUND));

        // AccessToken 이 유효한 경우
        if (jwtTokenProvider.vaildAccessToken(accessToken)) { // AccessToken 이 유효한 경우
            if (!jwtTokenProvider.vaildRefreshToken(member.getRefreshToken())) {// RefreshToken 은 만료된 경우
                String newRefreshToken = authTokensGenerator.generateRefreshToken(memberIdx.toString()); // RefreshToken 재발급
                memberRepository.saveRefreshToken(newRefreshToken, memberIdx);
            }
            return true;
        } else {
            if (jwtTokenProvider.vaildRefreshToken(member.getRefreshToken())) { // RefreshToken 이 유효한지 검증했는데 유효한 경우
                String newAccessToken = authTokensGenerator.generateAccessToken(memberIdx.toString()); // AccessToken 재발급
                System.out.println("RefreshToken은 만료되지 않았으므로 새로운 AccessToken을 발급함 " + newAccessToken);
                response.setHeader("newAccessToken", newAccessToken);
                return true;
            }
        }

        // AccessToken, RefreshToken 둘 다 유효하지 않은 경우 -> 재 로그인 필요
        throw new RestApiException(ErrorCode.VALID_TOKEN_EXPIRED);
    }
}