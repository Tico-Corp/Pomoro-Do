package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.entity.Refresh;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.enums.TokenType;

public interface TokenService {

    // 리프레쉬 토큰 저장
    void addRefreshEntity(String username, String refresh, Long expiredMs, String deviceId);

    // 토큰 엔티티 가져오기
    Refresh getRefreshByDeviceId(String deviceId);
    Refresh getRefreshByRefreshToken(String refreshToken);

    // 토큰 삭제
    void deleteRefreshTokenByDeviceId(String username, String deviceId, String refreshHeader);
    void deleteAllRefreshTokensByUsername(String username, String deviceId, String refreshHeader);

    // 주어진 토큰 타입에 대한 토큰 검증 SuccessCode를 반환
    SuccessCode getSuccessCodeForTokenType(TokenType tokenType);
}
