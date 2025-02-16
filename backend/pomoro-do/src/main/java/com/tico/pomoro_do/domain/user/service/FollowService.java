package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.UserProfileResponse;

import java.util.List;

public interface FollowService {

    /**
     * 특정 사용자를 팔로우
     *
     * @param followerId 팔로우하는 사용자 ID
     * @param followingId 팔로우 대상 사용자 ID
     */
    void followUser(Long followerId, Long followingId);

    /**
     * 특정 사용자를 언팔로우
     *
     * @param followerId 팔로우 취소하는 사용자 ID
     * @param followingId 팔로우 대상 사용자 ID
     */
    void unfollowUser(Long followerId, Long followingId);

    /**
     * 사용자를 팔로우하는 사용자 목록 조회 (팔로워 조회)
     *
     * @param userId 현재 사용자 ID
     * @return 사용자를 팔로우하는 사용자 목록 (UserProfileResponse 리스트)
     */
    List<UserProfileResponse> getFollowers(Long userId);

    /**
     * 사용자가 팔로우하는 사용자 목록 조회 (팔로잉 조회)
     *
     * @param userId 현재 사용자 ID
     * @return 사용자가 팔로우하는 사용자 목록 (UserProfileResponse 리스트)
     */
    List<UserProfileResponse> getFollowings(Long userId);

    /**
     * 특정 사용자가 타겟 사용자를 팔로우하고 있는지 여부
     *
     * @param currentUserId 현재 인증된 사용자 ID
     * @param targetUserId 팔로우 여부를 확인할 대상의 사용자 ID
     * @return 팔로우 여부 반환 (true: 팔로우 중, false: 팔로우 안 함)
     */
    boolean isFollowing(Long currentUserId, Long targetUserId);
}
