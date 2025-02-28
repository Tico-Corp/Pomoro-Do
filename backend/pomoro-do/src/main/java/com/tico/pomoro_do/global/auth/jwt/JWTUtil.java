package com.tico.pomoro_do.global.auth.jwt;

import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.domain.auth.enums.TokenType;
import com.tico.pomoro_do.global.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

//JWT 토큰 발급 및 검증 (로그인-인증 과정)
@Component
@Slf4j
public class JWTUtil {

    // 객체키
    private SecretKey secretKey;

    // 객체변수로 암호화
    public JWTUtil(@Value("${jwt.secret-key}")String secret) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // 토큰 검증 함수

    // 토큰 검증 -> 클램 확인 후 -> 특정데이터 가져오기

//    // 유저 이름 확인 메서드
//    public String getUsername(String token) {
//        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
//    }

    public Long getUserId(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", Long.class);
    }

    // 유저 이메일 확인 메서드 -> JWT에서 사용자 이메일 추출
    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    // 유저 역할 확인 메서드
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    // 토큰의 카테고리 확인 메서드
    public String getCategory(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    // 토큰 만료 확인 메서드
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    // 토큰 생성 메서드 (카테고리, 유저 이메일, 역할, 만료시간)
    public String createJwt(String category, Long userId, String email, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("category", category) //access인지? refesh인지?
                .claim("userId", userId)
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 생성 시각
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) // 만료 시각
                .signWith(secretKey)
                .compact();
    }

    // 토큰 추출 메서드
    /**
     * 주어진 헤더에서 토큰을 추출
     *
     * @param header HTTP 헤더 문자열 (예: "Bearer <token>")
     * @param tokenType 토큰 타입 (예: ACCESS, REFRESH, GOOGLE)
     * @return 추출된 토큰 문자열
     * @throws CustomException 잘못된 헤더 형식 시 발생하는 예외
     *                         - 헤더가 null이거나 비어있는 경우
     *                         - 헤더 형식이 "Bearer <token>" 형식이 아닌 경우
     *                         - 토큰 타입이 Google ID 토큰인데 헤더 형식이 맞지 않는 경우
     *                         - JWT ACCESS 또는 REFRESH 토큰의 경우 헤더 형식이 맞지 않는 경우
     */
    public String extractToken(String header, TokenType tokenType) {

        //  header가 "Bearer "로 시작하지 않는 경우 빈 문자열 ("")도 이 조건에 의해 걸러지므로 header.isEmpty() 불필요하다.
        if (header == null || !header.startsWith("Bearer ")) {
            ErrorCode errorCode;

            switch (tokenType) {
                case GOOGLE:
                    errorCode = ErrorCode.INVALID_GOOGLE_TOKEN_HEADER;
                    break;
                case ACCESS:
                    errorCode = ErrorCode.INVALID_AUTHORIZATION_HEADER;
                    break;
                case REFRESH:
                    errorCode = ErrorCode.INVALID_REFRESH_TOKEN_HEADER;
                    break;
                default:
                    errorCode = ErrorCode.INVALID_AUTHORIZATION_HEADER; // 기본값 설정
                    break;
            }

            throw new CustomException(errorCode);
        }

        return header.substring(7);
    }

    // 토큰 검증 메서드
    /**
     * 주어진 토큰을 검증
     *
     * @param token 검증할 토큰
     * @param expectedCategory 예상되는 토큰 카테고리 (예: ACCESS, REFRESH)
     * @throws CustomException 검증 실패 시 발생하는 예외
     */
    public void validateToken(String token, TokenType expectedCategory) {

        if (token == null) {
            log.error("토큰이 없습니다. 카테고리 = {}", expectedCategory);
            throw new CustomException(
                    expectedCategory == TokenType.ACCESS
                            ? ErrorCode.MISSING_ACCESS_TOKEN
                            : ErrorCode.MISSING_REFRESH_TOKEN
            );
        }

        // 토큰 만료 및 서명 오류 확인
        validateTokenSignature(token, expectedCategory);

        // 토큰 카테고리 확인
        String category = getCategory(token);
        if (!category.equals(expectedCategory.name())) {
            log.error("토큰 카테고리 불일치: 예상 = {}, 실제 = {}", expectedCategory, category);
            throw new CustomException(ErrorCode.INVALID_TOKEN_TYPE);
        }

        log.info("{} 토큰 검증 완료", expectedCategory);
    }

    /**
     * 토큰의 서명 및 유효성 검증
     *
     * @param token 검증할 토큰
     * @param expectedCategory 예상되는 토큰 카테고리 (예: ACCESS, REFRESH)
     * @throws CustomException 서명 및 유효성 검증 실패 시 발생하는 예외
     */
    private void validateTokenSignature(String token, TokenType expectedCategory) {
        try {
            isExpired(token);
        } catch (ExpiredJwtException e) {
            log.error("토큰 만료됨: 카테고리 = {}, 이유 = {}", expectedCategory, e.getMessage());
            throw new CustomException(
                    expectedCategory == TokenType.ACCESS
                            ? ErrorCode.ACCESS_TOKEN_EXPIRED
                            : ErrorCode.REFRESH_TOKEN_EXPIRED
            );
        } catch (SignatureException e) {
            log.error("유효하지 않은 JWT 서명: 카테고리 = {}, 이유 = {}", expectedCategory, e.getMessage());
            throw new CustomException(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 형식: 카테고리 = {}, 이유 = {}", expectedCategory, e.getMessage());
            throw new CustomException(ErrorCode.INVALID_MALFORMED_JWT);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT: 카테고리 = {}, 이유 = {}", expectedCategory, e.getMessage());
            throw new CustomException(ErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰: 카테고리 = {}, 이유 = {}", expectedCategory, e.getMessage());
            throw new CustomException(ErrorCode.ILLEGAL_ARGUMENT);
        }
    }

}