package com.tico.pomoro_do.global.util;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//JWT 토큰 발급 및 검증 (로그인-인증 과정)
@Component
public class JWTUtil {

    //객체키
    private SecretKey secretKey;

    //객체변수로 암호화
    public JWTUtil(@Value("${jwt.secret-key}")String secret) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //토큰 검증 함수

    //토큰 검증 -> 클램 확인 후 -> 특정데이터 가져오기

    //유저 이름
    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    //유저 역할
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    //토큰 만료 확인
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    //토큰 생성 함수
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 생성 시각
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 시각
                .signWith(secretKey)
                .compact();
    }
}