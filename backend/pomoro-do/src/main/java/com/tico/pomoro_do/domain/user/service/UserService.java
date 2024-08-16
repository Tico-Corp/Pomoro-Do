package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;

public interface UserService {

    // 내 정보 조회
    UserDetailDTO getMyDetail(String username);

    // 유저 조회
    UserDetailDTO getUserDetail(Long userId);

    // 유저 삭제
    void deleteUser(String username, String deviceId, String refreshHeader);
}
