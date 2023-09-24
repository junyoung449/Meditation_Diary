package com.project.mt.authentication.infra;

import java.security.Key;
import java.util.Date;

import com.project.mt.exception.ErrorCode;
import com.project.mt.exception.RestApiException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    private final Key key;

    @Value("${jwt.secret-key}")
    private String secretKey;

    public JwtTokenProvider(@Value("${jwt.secret-key}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     *
     * @param subject : memberIdx
     * @param expiredAt : 만료일자
     * @return
     */
    public String generate(String subject, Date expiredAt) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiredAt)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * accessToken 에서 memberIdx 를 추출함
     */
    public String extractSubject(String accessToken) {
        Claims claims = parseClaims(accessToken);
        return claims.getSubject();
    }

    public boolean vaildAccessToken(String accessToken) {
        try {
            Claims claims =  Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(accessToken)
                    .getBody();
            return true;  //유효하다면 true 반환
        } catch (MalformedJwtException e) {
            return false;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public boolean vaildRefreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(refreshToken)
                    .getBody();
            return true;  //유효하다면 true 반환
        } catch (MalformedJwtException e) {
            throw new RestApiException(ErrorCode.UNAUTHORIZED_REQUEST);
        } catch (ExpiredJwtException e) {
            throw new RestApiException(ErrorCode.VALID_TOKEN_EXPIRED);
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

