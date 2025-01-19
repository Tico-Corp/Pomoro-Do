package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;

import java.util.List;

public interface FollowService {

    /**
     * 특정 사용자 팔로우
     *
     * @param followerUsername 팔로우하는 사용자 이름
     * @param followingId   팔로우될 사용자 ID
     */
    void follow(String followerUsername, Long followingId);

    /**
     * 특정 사용자 팔로우 취소
     *
     * @param followerUsername 현재 사용자 이름
     * @param followingId   팔로우 취소하는 사용자 ID
     */
    void unfollow(String followerUsername, Long followingId);

    /**
     * 사용자를 팔로우 중인 사용자 목록을 조회
     *
     * @param username 현재 인증된 사용자의 사용자 이름
     * @return FollowResponse 객체 리스트 반환
     */
    List<FollowResponse> getFollowers(String username);

    /**
     * 사용자가 팔로우 중인 사용자 목록을 조회
     *
     * @param username 현재 인증된 사용자의 사용자 이름
     * @return FollowResponse 객체 리스트 반환
     */
    List<FollowResponse> getFollowings(String username);

    /**
     * 특정 사용자가 타겟 사용자를 팔로우하고 있는지 여부
     *
     * @param currentUserId 현재 인증된 사용자의 ID
     * @param targetUserId 팔로우 여부를 확인할 대상 사용자의 ID
     * @return 팔로우 여부 반환
     */
    boolean isFollowing(Long currentUserId, Long targetUserId);
}
