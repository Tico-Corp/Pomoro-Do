package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowUserDTO;

import java.util.List;

public interface FollowService {

    // 팔로우
    void follow(String senderUsername, Long receiverId);

    // 내가 팔로우하고 있는 사용자 목록을 조회
    List<FollowUserDTO> getFollowingList(String username);
}
