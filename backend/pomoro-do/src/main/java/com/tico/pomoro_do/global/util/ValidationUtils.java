package com.tico.pomoro_do.global.util;

import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationUtils {

    /**
     * 디바이스 ID 검증 메서드
     *
     * @param deviceId 기기 고유 번호
     */
    public static void validateDeviceId(String deviceId) {
        if (deviceId == null || deviceId.isEmpty()) {
            log.error("유효하지 않은 입력: deviceId={}", deviceId);
            throw new CustomException(ErrorCode.DEVICE_ID_INVALID);
        }
    }

    /**
     * 닉네임 검증 메서드
     *
     * @param nickname 닉네임
     */
    public static void validateNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            log.error("유효하지 않은 입력: nickname={}", nickname);
            throw new CustomException(ErrorCode.NICKNAME_INVALID);
        }
        if (nickname.length() > 10) {
            log.error("닉네임이 너무 깁니다: nickname={}", nickname);
            throw new CustomException(ErrorCode.NICKNAME_TOO_LONG);
        }
    }
}