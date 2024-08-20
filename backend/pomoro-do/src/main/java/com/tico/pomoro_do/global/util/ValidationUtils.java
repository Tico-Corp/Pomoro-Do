package com.tico.pomoro_do.global.util;

import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class ValidationUtils {

    /**
     * User Id 검증 메서드
     *
     * @param userId 사용자 고유 번호
     * @throws CustomException 유효하지 않은 userId일 경우
     */
    public static void validateUserId(Long userId) {

        if (userId == null || userId <= 0) {
            log.error("유효하지 않은 userId: {}", userId);
            throw new CustomException(ErrorCode.USER_ID_INVALID);
        }
    }

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

    /**
     * 그룹 멤버 등록 검증 메서드
     *
     * @param members 지정한 그룹 멤버들 ID
     */
    public static void validateGroupMembers(Set<Long> members) {
        if (members == null || members.isEmpty()) {
            log.error("그룹 멤버가 지정되지 않음");
            throw new CustomException(ErrorCode.CATEGORY_MEMBER_NOT_FOUND);
        }
    }

}