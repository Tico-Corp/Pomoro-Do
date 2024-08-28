package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowUserDTO;

import java.util.List;

public interface FollowService {

    // 팔로우
    void follow(String senderUsername, Long receiverId);

    // 내가 팔로우하고 있는 사용자 목록을 조회
    List<FollowUserDTO> getFollowingList(String username);
    // 나를 팔로우하고 있는 사용자 목록을 조회
    List<FollowUserDTO> getFollowersList(String username);

    // 특정 사용자가 다른 사용자를 팔로우하고 있는지 확인
    boolean isFollowedByUser(Long senderId, Long receiverId);
}
