package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.UserProfileResponse;
import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.FollowRepository;
import com.tico.pomoro_do.global.exception.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.common.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class FollowServiceImpl implements FollowService {

    private final UserService userService;
    private final FollowRepository followRepository;

    // 특정 사용자를 팔로우
    @Override
    @Transactional
    public void followUser(Long followerId, Long followingId) {
        // 유효성 검사 (null 또는 음수 체크)
        ValidationUtils.validateUserId(followingId);

        User follower = userService.findUserById(followerId);
        User following = userService.findUserById(followingId);

        // 본인을 팔로우 하는 지 확인
        if (followerId.equals(followingId)) {
            log.warn("[Follow] 본인 팔로우 시도: userId={}", followerId);
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        // 이미 팔로우 중인지 확인
        if (isFollowing(followerId, followingId)) {
            log.warn("[Follow] 이미 팔로우 중: followerId={}, followingId={}", followerId, followingId);
            throw new CustomException(ErrorCode.ALREADY_FOLLOWED);
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(follow);

        log.info("[Follow] 팔로우 성공: followerId={}, followingId={}", followerId, followingId);
    }

    // 언팔로우
    @Override
    @Transactional
    public void unfollowUser(Long followerId, Long followingId) {
        // 유효성 검사 (null 또는 음수 체크)
        ValidationUtils.validateUserId(followingId);

        // 팔로워 유저 ID 검증
        userService.verifyUserExists(followerId);
        // 팔로이 유저 ID 검증
        userService.verifyUserExists(followingId);

        // 팔로우 관계 조회 및 확인
        Follow follow = findFollowOrThrow(followerId, followingId);

        // 팔로우 관계 삭제
        followRepository.delete(follow);

        log.info("[Follow] 언팔로우 성공: followerId={}, followingId={}", followerId, followingId);
    }

    // 나를 팔로우하고 있는 사용자 목록을 조회
    @Override
    public List<UserProfileResponse> getFollowers(Long userId) {
        // 사용자를 팔로우하는 사용자 목록을 조회
        return getFollowList(userId, false);
    }

    // 내가 팔로우하고 있는 사용자 목록을 조회
    @Override
    public List<UserProfileResponse> getFollowings(Long userId) {
        // 팔로우 중인 사용자 목록을 조회
        return getFollowList(userId, true);
    }

    /**
     * 팔로우 또는 팔로워 목록 조회
     *
     * @param userId 현재 사용자 ID
     * @param isFollowings true: 팔로잉 목록, false: 팔로워 목록
     * @return UserProfileResponse 리스트 반환
     */
    private List<UserProfileResponse> getFollowList(Long userId, boolean isFollowings) {
        // 사용자 정보 조회
        User user = userService.findUserById(userId);

        // 팔로우 목록 또는 팔로워 목록을 조회
        List<Follow> followList = isFollowings
                ? followRepository.findByFollower(user) // 팔로잉 목록 조회 (내가 팔로우하는 사람들)
                : followRepository.findByFollowing(user); // 팔로워 목록 조회 (나를 팔로우하는 사람들)

        // Follow 엔티티 리스트를 FollowResponse 리스트로 변환하여 반환
        return followList.stream()
                .map(follow -> convertToFollowResponse(follow, isFollowings, userId)) // DTO 변환
                .sorted(Comparator.comparing(UserProfileResponse::getNickname)) // 닉네임 기준으로 정렬
                .collect(Collectors.toList()); // 리스트로 수집하여 반환
    }

    /**
     * Follow 엔티티를 UserProfileResponse DTO로 변환
     *
     * @param follow Follow 엔티티
     * @param isFollowings 팔로우 목록 여부
     * @param currentUserId 현재 사용자 ID
     * @return UserProfileResponse DTO
     */
    private UserProfileResponse convertToFollowResponse(Follow follow, boolean isFollowings, Long currentUserId) {
        // 타겟 유저를 결정: 팔로우 목록일 경우 수신자, 팔로워 목록일 경우 발신자
        User targetUser = isFollowings ? follow.getFollowing() : follow.getFollower();

        // 현재 사용자가 해당 타겟 유저를 팔로우하고 있는지 여부를 확인
        boolean isFollowing = isFollowings || isFollowing(currentUserId, targetUser.getId());

        // FollowUserDTO 객체 생성 및 반환
        return UserProfileResponse.builder()
                .userId(targetUser.getId()) // 타겟 유저의 ID 설정
                .nickname(targetUser.getNickname()) // 타겟 유저의 닉네임 설정
                .profileImageUrl(targetUser.getProfileImageUrl()) // 타겟 유저의 프로필 이미지 URL 설정
                .following(isFollowing) // 팔로우 여부 설정
                .build();
    }

    // 특정 사용자가 대상 사용자를 팔로우하고 있는지 여부 확인
    @Override
    public boolean isFollowing(Long followerId, Long followingId) {
        return followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }

    /**
     * 팔로우 관계를 조회
     *
     * @param followerId 팔로우 요청자 ID
     * @param followingId 팔로우 대상자 ID
     * @return Follow 객체
     */
    private Follow findFollowOrThrow(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> {
                    log.warn("[Follow] 언팔로우 실패 (관계 없음): followerId={}, followingId={}", followerId, followingId);
                    return new CustomException(ErrorCode.NOT_FOLLOWING);
                });
    }
}
