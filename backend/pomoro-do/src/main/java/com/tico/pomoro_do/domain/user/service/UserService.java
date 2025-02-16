package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.UserProfileResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailResponse;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.exception.CustomException;

public interface UserService {

    // 사용자 ID로 사용자 조회
    User findUserById(Long userId);

    // 사용자 이메일로 사용자 조회
    User findUserByEmail(String email);

    // 내 프로필 조회
    UserDetailResponse getUserDetail(Long userId);

    // 특정 사용자 프로필 조회 (팔로우 상태 포함)
    UserProfileResponse getUserProfile(Long currentUserId, Long targetUserId);

    // 사용자 삭제
    void deleteUser(Long userId, String deviceId, String refreshHeader);

    /**
     * 이메일이 중복되지 않았는지 검증
     * @param email 검증할 이메일
     * @throws CustomException 이메일이 이미 존재하는 경우 예외 발생
     */
    void verifyEmailNotRegistered(String email);

    /**
     * 이메일이 존재하는지 검증
     * @param email 검증할 이메일
     * @throws CustomException 사용자를 찾을 수 없는 경우 예외 발생
     */
    void verifyEmailExists(String email);

    /**
     * 사용자 ID를 통해 등록된 사용자인지 검증
     *
     * @param userId 사용자 ID
     * @throws CustomException 등록되지 않은 사용자일 경우 예외 발생
     */
    void verifyUserExists(Long userId);
}