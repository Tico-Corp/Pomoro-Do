package com.tico.pomoro_do.domain.user.service;

public interface FollowService {

    // 팔로우
    void follow(String senderUsername, Long receiverId);
}
