package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {

    // 해당 리프레쉬 토큰의 존재 여부를 판단하는 메소드
    boolean existsByRefreshToken(String refreshToken);
    Optional<Refresh> findByDeviceId(String deviceId);

    Optional<Refresh> findByRefreshToken(String refreshToken);


    // 해당 리프레쉬 토큰을 삭제하는 메소드
    @Transactional
    void deleteByRefreshToken(String refreshToken);

    // 사용자 이름을 기준으로 모든 리프레쉬 토큰을 삭제하는 메소드
    @Transactional
    void deleteByUsername(String username);

    // 디바이스 id를 기준으로 모든 리프레쉬 토큰을 삭제하는 메소드
    @Transactional
    void deleteByDeviceId(String deviceId);
}