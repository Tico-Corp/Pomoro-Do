package com.tico.pomoro_do.domain.auth.service;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.auth.entity.RefreshToken;
import com.tico.pomoro_do.domain.auth.repository.RefreshTokenRepository;
import com.tico.pomoro_do.global.security.JwtTokenProvider;
import com.tico.pomoro_do.global.exception.ErrorCode;
import com.tico.pomoro_do.global.response.SuccessCode;
import com.tico.pomoro_do.domain.auth.enums.TokenType;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true) // (성능 최적화 - 읽기 전용에만 사용)
@RequiredArgsConstructor // 파이널 필드만 가지고 생성사 주입 함수 만듬 (따로 작성할 필요 없다.)
@Slf4j
public class TokenServiceImpl implements TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    // 토큰 생성 및 저장
    @Override
    public TokenResponse createAuthTokens(Long userId, String email, String role, String deviceId) {
        // 액세스 토큰 생성
        String accessToken = jwtTokenProvider.createJwt(TokenType.ACCESS.name(), userId, email, role, accessExpiration); // 60분
        // 리프레시 토큰 생성
        String refreshToken = jwtTokenProvider.createJwt(TokenType.REFRESH.name(), userId, email, role, refreshExpiration); // 24시간

        // DB에서 deviceId에 해당하는 기존 리프레시 토큰을 삭제
        deleteRefreshTokenByDeviceId(deviceId);
        // 리프레시 토큰을 DB에 저장
        createRefreshToken(userId, refreshToken, refreshExpiration, deviceId);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public void createRefreshToken(Long userId, String refresh, Long expiredMs, String deviceId) {

        // 현재 시간에 만료 시간(밀리초)을 더해서 만료 날짜 계산
        LocalDateTime expiresAt = LocalDateTime.now().plus(Duration.ofMillis(expiredMs));

        RefreshToken refreshTokenEntity = RefreshToken.builder()
                .userId(userId)
                .refreshToken(refresh)
                .deviceId(deviceId)
                .expiresAt(expiresAt)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);
        log.info("리프레시 토큰 저장 성공: 사용자ID = {}, 토큰 = {}, 기기 고유번호 = {}", userId, refresh, deviceId);

    }

    @Override
    public RefreshToken getRefreshByDeviceId(String deviceId) {
        return refreshTokenRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> {
                    log.error("Device ID가 DB에 존재하지 않음: deviceId = {}", deviceId);
                    return new CustomException(ErrorCode.DEVICE_ID_NOT_FOUND);
                });
    }

    @Override
    public RefreshToken getRefreshByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> {
                    log.error("리프레시 토큰이 DB에 존재하지 않음: refreshToken = {}", refreshToken);
                    return new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                });
    }

    // 만료기간이 지난 토큰은 스케줄러를 돌려서 삭제하라.
    @Override
    public void deleteRefreshTokenByDeviceId(String deviceId) {
        // DB에서 deviceId에 해당하는 엔티티를 제거합니다.
        refreshTokenRepository.deleteByDeviceId(deviceId);
    }

    @Override
    public void deleteAllRefreshTokensByUserId(Long userId) {
        // DB에서 사용자Id에 해당하는 모든 엔티티를 제거합니다.
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Override
    public void validateRefreshTokenDetails(String refreshHeader, String deviceId, Long userId) {
        // 1. 토큰 검증
        String refresh = extractAndValidateToken(refreshHeader, TokenType.REFRESH);
        // 2. DB에서 토큰 조회
        RefreshToken refreshToken = getRefreshByRefreshToken(refresh);
        // 3. DB 데이터 검증
        // Device ID 검증
        validateDeviceIdMatch(refreshToken, deviceId);
        // userId 검증
        validateUserIdMatch(refreshToken, userId);
    }

    @Override
    public String validateAndGetRefreshToken(String refreshHeader, String deviceId) {
        // 1. 토큰 검증
        String refresh = extractAndValidateToken(refreshHeader, TokenType.REFRESH);
        // 2. DB에서 토큰 조회
        RefreshToken refreshToken = getRefreshByRefreshToken(refresh);
        // 3. DB 데이터 검증
        // Device ID 검증
        validateDeviceIdMatch(refreshToken, deviceId);

        return refresh;
    }

    // 토큰 검증만 수행
    private String extractAndValidateToken(String tokenHeader, TokenType tokenType) {
        // 토큰 추출
        String token = jwtTokenProvider.extractToken(tokenHeader, tokenType);
        // 토큰 유효성 검증
        jwtTokenProvider.validateToken(token, tokenType);
        // 유효한 토큰 반환
        return token;
    }

    // Device ID 검증
    private void validateDeviceIdMatch(RefreshToken refreshToken, String deviceId) {
        if (!refreshToken.getDeviceId().equals(deviceId)) {
            log.error("Device ID가 일치하지 않음: deviceId = {}", deviceId);
            throw new CustomException(ErrorCode.DEVICE_ID_MISMATCH);
        }
    }

    // JWT userId 검증
    private void validateUserIdMatch(RefreshToken refreshToken, Long userId) {
        if (!refreshToken.getUserId().equals(userId)) {
            log.error("userId가 일치하지 않음: userId = {}", userId);
            throw new CustomException(ErrorCode.USERNAME_MISMATCH);
        }
    }

    // 주어진 토큰 타입에 대한 토큰 검증 SuccessCode를 반환
    @Override
    public SuccessCode validateTokenAndGetSuccessCode(String tokenHeader, TokenType tokenType) {
        // 토큰 검증
        String token = extractAndValidateToken(tokenHeader, tokenType);

        return switch (tokenType) {
            case ACCESS -> SuccessCode.ACCESS_TOKEN_VALIDATED;
            case REFRESH -> SuccessCode.REFRESH_TOKEN_VALIDATED;
            default -> throw new IllegalArgumentException("지원하지 않는 토큰 타입: " + tokenType);
        };
    }

}
