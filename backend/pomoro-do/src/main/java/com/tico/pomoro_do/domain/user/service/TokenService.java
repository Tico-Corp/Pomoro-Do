package com.tico.pomoro_do.domain.user.service;

public interface TokenService {

    // 리프레쉬 토큰 저장
    void addRefreshEntity(String username, String refresh, Long expiredMs);
}
