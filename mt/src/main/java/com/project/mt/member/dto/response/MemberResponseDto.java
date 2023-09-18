package com.project.mt.member.dto.response;

import com.project.mt.authentication.domain.oauth.OAuthProvider;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class MemberResponseDto {
    private Long memberIdx;
    private String email;
    private String name;
    private String refreshToken;
    private OAuthProvider oauthProvider;
}
