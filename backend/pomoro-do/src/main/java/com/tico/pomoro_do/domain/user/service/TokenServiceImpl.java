package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.entity.Refresh;
import com.tico.pomoro_do.domain.user.repository.RefreshRepository;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    /**
     * 주어진 토큰을 검증
     *
     * @param token 검증할 토큰
     * @param expectedCategory 예상되는 토큰 카테고리 (예: "access" 또는 "refresh")
     * @throws CustomException 검증 실패 시 발생하는 예외
     */
    @Override
    public void validateToken(String token, String expectedCategory) {
        log.info(expectedCategory + " 토큰 검증 시작: token = {}", token);

        if (token == null) {
            log.error("토큰이 null입니다. 카테고리 = {}", expectedCategory);
            throw new CustomException(
                    expectedCategory.equals("access")
                            ? ErrorCode.MISSING_ACCESS_TOKEN
                            : ErrorCode.MISSING_REFRESH_TOKEN
            );
        }

        // 토큰 만료 및 서명 오류 확인
        try {
            jwtUtil.isExpired(token);
        } catch (ExpiredJwtException e) {
            log.error("토큰 만료됨: 카테고리 = {}", expectedCategory);
            throw new CustomException(
                    expectedCategory.equals("access")
                            ? ErrorCode.ACCESS_TOKEN_EXPIRED
                            : ErrorCode.REFRESH_TOKEN_EXPIRED
            );
        } catch (SignatureException e) {
            log.error("유효하지 않은 JWT 서명: 카테고리 = {}", expectedCategory);
            throw new CustomException(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 형식: 카테고리 = {}", expectedCategory);
            throw new CustomException(ErrorCode.INVALID_MALFORMED_JWT);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT: 카테고리 = {}", expectedCategory);
            throw new CustomException(ErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 토큰: 카테고리 = {}", expectedCategory);
            throw new CustomException(ErrorCode.ILLEGAL_ARGUMENT);
        }

        // 토큰 카테고리 확인
        String category = jwtUtil.getCategory(token);
        if (!category.equals(expectedCategory)) {
            log.error("토큰 카테고리 불일치: 예상 = {}, 실제 = {}", expectedCategory, category);
            throw new CustomException(
                    expectedCategory.equals("access")
                            ? ErrorCode.INVALID_ACCESS_TOKEN
                            : ErrorCode.INVALID_REFRESH_TOKEN
            );
        }

        log.info(expectedCategory + " 토큰 검증 완료");
    }

    //만료기간이 지난 토큰은 스케줄러를 돌려서 삭제하라.
    /**
     * 로그아웃 시 리프레시 토큰 삭제
     *
     * @param deviceId 기기 고유 번호
     * @param refreshToken 리프레시 토큰
     */
    @Transactional
    @Override
    public void removeRefreshToken(String deviceId, String refreshToken) {
        // 리프레시 토큰이 없는 경우, 로그를 기록하고 예외를 발생시킵니다.
        if (refreshToken == null || refreshToken.isEmpty()) {
            log.warn("리프레시 토큰을 입력해주세요.");
            throw new CustomException(ErrorCode.MISSING_REFRESH_TOKEN);
        }

        // 리프레시 토큰을 검증합니다.
        validateToken(refreshToken, "refresh");

        // DB에서 리프레시 토큰에 해당하는 리프레시 토큰 정보를 가져옵니다.
        Refresh refreshEntity = getRefreshByRefreshToken(refreshToken);

        // DB에 저장된 deviceId와 요청된 deviceId이 일치하는지 확인합니다.
        validateDeviceId(refreshEntity, deviceId);

        log.info("로그아웃: 리프레시 토큰 삭제 시작");
        // DB에서 리프레시 토큰을 제거합니다.
        refreshRepository.deleteByRefreshToken(refreshToken);
        log.info("로그아웃: 리프레시 토큰 삭제 완료");
    }

}
