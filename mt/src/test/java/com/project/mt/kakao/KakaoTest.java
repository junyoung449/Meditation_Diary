package com.project.mt.kakao;

import com.project.mt.authentication.domain.AuthTokensGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
public class KakaoTest {

    // @Autowired
    AuthTokensGenerator authTokensGenerator;

    @Test
    @DisplayName("엑세스 토큰으로 DB에 있는 유저 idx 받아오는지 확인하는 테스트")
    public void 카카오_엑세스토큰_테스트() {
        String accessToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjk0ODY1NzQ2fQ.3WUjFWskZf0gqHn_KyKoU8-4UZUKezbhTct3dz_O1cyv0qgxggATMQzZZt0iTGLPaqY_26kt5kC2TD-3mXuT7Q";

        Long memberIdx = authTokensGenerator.extractMemberId(accessToken);

        Assertions.assertThat(memberIdx).isEqualTo(1);
    }
}
