package com.project.mt.authentication.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.mt.authentication.domain.AuthTokens;
import com.project.mt.authentication.infra.kakao.KakaoLoginParams;

import lombok.RequiredArgsConstructor;


/**
 * 카카오 서버에서 받은 인증코드를 가지고 들어오는 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final OAuthLoginService oAuthLoginService;

    /**
     * @param params : 소셜 서비스, 인가코드를 가지고있는 파라미터
     * @return
     */
    @PostMapping("/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params) {
        System.out.println(params.getAuthorizationCode());
        return ResponseEntity.ok(oAuthLoginService.login(params));
    }

}
