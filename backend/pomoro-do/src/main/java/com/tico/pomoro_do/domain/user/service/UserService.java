package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailResponse;
import com.tico.pomoro_do.domain.user.entity.User;

public interface UserService {

    // 내 정보 조회
    UserDetailResponse getMyDetail(String email);

    // 유저 이름으로 조회
    User findByEmail(String email);

    // 유저 아이디로 조회
    User findByUserId(Long userId);

    // 특정 유저 조회
    FollowResponse getUserDetail(String email, Long userId);

    // 유저 삭제
    void deleteUser(String email, String deviceId, String refreshHeader);

}
