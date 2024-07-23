package com.tico.pomoro_do.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenService {

    // 리프레쉬 토큰 저장
    void addRefreshEntity(String username, String refresh, Long expiredMs);

    // 토큰 검증
    void validateToken(String token, String expectedCategory);

    // 토큰 삭제
    void removeRefreshToken(HttpServletRequest request, HttpServletResponse response);
}
