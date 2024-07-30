package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.entity.Refresh;
import com.tico.pomoro_do.domain.user.repository.RefreshRepository;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.exception.CustomException;
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

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    /**
     * 새로운 리프레시 토큰 저장
     *
     * @param username 사용자 이메일
     * @param refresh 새로운 리프레시 토큰
     * @param expiredMs 토큰 만료 시간 (밀리초 단위)
     */
    @Transactional
    @Override
    public void addRefreshEntity(String username, String refresh, Long expiredMs, String deviceId) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        Refresh refreshEntity = Refresh.builder()
                .username(username)
                .refreshToken(refresh)
                .expiration(date.toString())
                .deviceId(deviceId)
                .build();

        refreshRepository.save(refreshEntity);
        log.info("리프레시 토큰 저장 성공: 사용자 = {}, 토큰 = {}, 기기 고유번호 = {}", username, refresh, deviceId);

    }

    /**
     * 주어진 deviceId로 리프레시 토큰 엔티티를 가져옵니다.
     *
     * @param deviceId 기기 고유 번호
     * @return Refresh 엔티티
     * @throws CustomException 기기 ID가 DB에 존재하지 않을 때 발생하는 예외
     */
    @Override
    public Refresh getRefreshByDeviceId(String deviceId) {
        return refreshRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> {
                    log.error("Device ID가 DB에 존재하지 않음: deviceId = {}", deviceId);
                    return new CustomException(ErrorCode.DEVICE_ID_NOT_FOUND);
                });
    }

    /**
     * 주어진 리프레시 엔티티와 deviceId를 검증합니다.
     *
     * @param refreshEntity 리프레시 엔티티
     * @param deviceId 기기 고유 번호
     * @throws CustomException deviceId 불일치 시 발생하는 예외
     */
    private void validateDeviceId(Refresh refreshEntity, String deviceId) {
        if (!refreshEntity.getDeviceId().equals(deviceId)) {
            log.error("Device ID가 DB에 존재하지 않음: deviceId = {}", deviceId);
            throw new CustomException(ErrorCode.DEVICE_ID_MISMATCH);
        }
    }

    /**
     * 주어진 리프레시 토큰으로 리프레시 토큰 엔티티를 가져옵니다.
     *
     * @param refreshToken 리프레시 토큰
     * @return Refresh 엔티티
     * @throws CustomException 리프레시 토큰이 DB에 존재하지 않을 때 발생하는 예외
     */
    @Override
    public Refresh getRefreshByRefreshToken(String refreshToken) {
        return refreshRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> {
                    log.error("리프레시 토큰이 DB에 존재하지 않음: refreshToken = {}", refreshToken);
                    return new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
                });
    }

    // 만료기간이 지난 토큰은 스케줄러를 돌려서 삭제하라.
    /**
     * 로그아웃 시 리프레시 토큰 삭제
     *
     * @param deviceId 기기 고유 번호
     * @param refreshHeader 리프레시 토큰
     */
    @Override
    @Transactional
    public void removeRefreshToken(String deviceId, String refreshHeader) {
        // 헤더를 검증합니다.
        String refresh = jwtUtil.extractToken(refreshHeader, TokenType.REFRESH);
        // 리프레시 토큰을 검증합니다.
        jwtUtil.validateToken(refresh, TokenType.REFRESH);

        // DB에서 리프레시 토큰에 해당하는 리프레시 토큰 정보를 가져옵니다.
        Refresh refreshEntity = getRefreshByRefreshToken(refresh);

        // DB에 저장된 deviceId와 요청된 deviceId이 일치하는지 확인합니다.
        validateDeviceId(refreshEntity, deviceId);

        log.info("로그아웃: 리프레시 토큰 삭제 시작");
        // DB에서 리프레시 토큰을 제거합니다.
        refreshRepository.deleteByRefreshToken(refresh);
        log.info("로그아웃: 리프레시 토큰 삭제 완료");
    }

    /**
     * 주어진 토큰 타입에 대한 SuccessCode를 반환
     *
     * @param tokenType 검증할 토큰 타입
     * @return SuccessCode
     */
    @Override
    public SuccessCode getSuccessCodeForTokenType(TokenType tokenType) {
        switch (tokenType) {
            case ACCESS:
                return SuccessCode.ACCESS_TOKEN_VALIDATED;
            case REFRESH:
                return SuccessCode.REFRESH_TOKEN_VALIDATED;
            default:
                throw new IllegalArgumentException("지원하지 않는 토큰 타입: " + tokenType);
        }
    }

}
