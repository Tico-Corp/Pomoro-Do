package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.entity.Refresh;
import com.tico.pomoro_do.domain.user.repository.RefreshRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true) // (성능 최적화 - 읽기 전용에만 사용)
@RequiredArgsConstructor // 파이널 필드만 가지고 생성사 주입 함수 만듬 (따로 작성할 필요 없다.)
@Slf4j
public class TokenServiceImpl implements TokenService{

    private final RefreshRepository refreshRepository;

    @Transactional
    @Override
    public void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = Refresh.builder()
                .username(username)
                .refreshToken(refresh)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refreshEntity);
    }
}
