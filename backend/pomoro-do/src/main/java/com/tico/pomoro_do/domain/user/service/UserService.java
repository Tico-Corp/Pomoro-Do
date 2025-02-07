package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailResponse;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.exception.CustomException;

public interface UserService {

    // 사용자 아이디로 조회
    User findByUserId(Long userId);

    // 사용자 이메일로 조회
    User findByEmail(String email);

    // 내 정보 조회
    UserDetailResponse getMyDetail(String email);

    // 특정 사용자 정보 조회
    FollowResponse getUserDetail(String email, Long userId);

    // 유저 삭제
    void deleteUser(Long userId, String deviceId, String refreshHeader);

    /**
     * 이메일을 통해 사용자가 이미 등록되어 있는지 확인
     *
     * @param email 사용자 이메일
     * @throws CustomException 이미 등록된 사용자인 경우 예외를 던짐
     */
    void isEmailRegistered(String email);

    /**
     * 이메일을 통해 사용자가 등록되어 있는지 검증
     *
     * @param email 사용자 이메일
     * @throws CustomException 등록되지 않은 사용자일 경우 예외를 던짐
     */
    void validateEmailExists(String email);
}
