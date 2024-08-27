package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowUserDTO;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;
import com.tico.pomoro_do.domain.user.entity.User;

public interface UserService {

    // 내 정보 조회
    UserDetailDTO getMyDetail(String username);

    // 유저 이름으로 조회
    User findByUsername(String username);

    // 유저 아이디로 조회
    User findByUserId(Long userId);

    // 특정 유저 조회
    FollowUserDTO getUserDetail(String username, Long userId);

    // 유저 삭제
    void deleteUser(String username, String deviceId, String refreshHeader);

}
