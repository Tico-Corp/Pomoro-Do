package com.tico.pomoro_do.domain.auth.repository;

import com.tico.pomoro_do.domain.auth.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    // 해당 리프레쉬 토큰의 존재 여부를 판단하는 메소드
    Optional<RefreshToken> findByDeviceId(String deviceId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    // 해당 리프레쉬 토큰을 삭제하는 메소드
    void deleteByRefreshToken(String refreshToken);

    // 사용자 이름을 기준으로 모든 리프레쉬 토큰을 삭제하는 메소드
    void deleteByEmail(String email);

    // 디바이스 id를 기준으로 모든 리프레쉬 토큰을 삭제하는 메소드
    void deleteByDeviceId(String deviceId);
}