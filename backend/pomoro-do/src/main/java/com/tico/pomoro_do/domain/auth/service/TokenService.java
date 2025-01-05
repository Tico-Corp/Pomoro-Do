package com.tico.pomoro_do.domain.auth.service;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.auth.entity.RefreshToken;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.exception.CustomException;

public interface TokenService {


    /**
     * 사용자 인증을 위한 액세스 토큰과 리프레시 토큰을 생성하고 저장
     *
     * @param username 사용자 이메일
     * @param role 사용자 역할
     * @param deviceId 기기 고유 번호
     * @return TokenDTO 객체, 생성된 액세스 토큰을 포함
     */
    TokenResponse createAuthTokens(String username, String role, String deviceId);

    /**
     * 새로운 리프레시 토큰을 저장합니다.
     *
     * @param username 사용자 이메일
     * @param refresh 새로운 리프레시 토큰
     * @param expiredMs 토큰 만료 시간 (밀리초 단위)
     * @param deviceId 기기 고유 번호
     */
    void createRefreshToken(String username, String refresh, Long expiredMs, String deviceId);

    // 토큰 엔티티 가져오기
    /**
     * 주어진 deviceId로 리프레시 토큰 엔티티를 가져옵니다.
     *
     * @param deviceId 기기 고유 번호
     * @throws CustomException 기기 ID가 DB에 존재하지 않을 때 발생하는 예외
     */
    RefreshToken getRefreshByDeviceId(String deviceId);

    /**
     * 주어진 리프레시 토큰으로 리프레시 토큰 엔티티를 가져옵니다.
     *
     * @param refreshToken 리프레시 토큰
     * @throws CustomException 리프레시 토큰이 DB에 존재하지 않을 때 발생하는 예외
     */
    RefreshToken getRefreshByRefreshToken(String refreshToken);

    // 토큰 검증
    /**
     * 리프레시 토큰을 검증하고, 유효성을 확인합니다.
     *
     * @param refreshHeader 리프레시 토큰 헤더
     * @param deviceId 기기 고유 번호
     * @param username 사용자 이름
     */
    void validateRefreshTokenDetails(String refreshHeader, String deviceId, String username);
    String validateAndGetRefreshToken(String refreshHeader, String deviceId);


    // 토큰 삭제
    /**
     * 로그아웃 시 특정 기기에서의 리프레시 토큰 삭제
     *
     * @param deviceId 기기 고유 번호
     */
    void deleteRefreshTokenByDeviceId(String deviceId);

    /**
     * 회원탈퇴 시 회원의 모든 리프레시 토큰 삭제
     *
     * @param username 사용자 이름
     */
    void deleteAllRefreshTokensByUsername(String username);

    /**
     * 주어진 토큰을 검증하고 토큰 타입에 대한 SuccessCode 반환
     *
     * @param tokenHeader jwt 토큰 헤더
     * @param tokenType 검증할 토큰 타입
     */
    SuccessCode validateTokenAndGetSuccessCode(String tokenHeader, TokenType tokenType);
}
