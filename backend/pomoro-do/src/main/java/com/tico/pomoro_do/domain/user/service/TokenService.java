package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.entity.Refresh;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {

    // 리프레쉬 토큰 저장
    void addRefreshEntity(String username, String refresh, Long expiredMs, String deviceId);

    // 토큰 엔티티 가져오기
    Refresh getRefreshByDeviceId(String deviceId);
    Refresh getRefreshByRefreshToken(String refreshToken);

    // 기기 고유번호 검증
//    void validateDeviceId(Refresh refreshEntity, String deviceId);

    // 토큰 검증
    void validateToken(String token, String expectedCategory);

    // 토큰 삭제
    void removeRefreshToken(String deviceId, String refreshToken);
}
