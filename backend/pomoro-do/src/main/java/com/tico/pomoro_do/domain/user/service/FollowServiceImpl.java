package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;
import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.FollowRepository;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.util.ValidationUtils;
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
    public void follow(String followerUserEmail, Long followingId) {
        // 유효성 검사 (null 또는 음수 체크)
        ValidationUtils.validateUserId(followingId);

        User follower = userService.findByEmail(followerUserEmail);
        User following = userService.findByUserId(followingId);

        // 본인을 팔로우 하는 지 확인
        if (follower.equals(following)) {
            log.warn("사용자 '{}'가 본인을 팔로우하려고 시도했습니다.", followerUserEmail);
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        // 이미 팔로우 중인지 확인
        if (isFollowing(follower.getId(), followingId)) {
            log.warn("사용자 '{}'가 이미 사용자 ID '{}'를 팔로우했습니다.", followerUserEmail, followingId);
            throw new CustomException(ErrorCode.ALREADY_FOLLOWED);
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(follow);
    }

    // 언팔로우
    @Override
    @Transactional
    public void unfollow(String followerUserEmail, Long followingId) {
        // 유효성 검사 (null 또는 음수 체크)
        ValidationUtils.validateUserId(followingId);

        // 팔로워 유저 조회
        User follower = userService.findByEmail(followerUserEmail);
        // 팔로이 유저 조회
        User following = userService.findByUserId(followingId);

        // 팔로우 관계 조회 및 확인
        Follow follow = getFollowRelationship(follower.getId(), followingId);

        // 팔로우 관계 삭제
        followRepository.delete(follow);
    }

    // 나를 팔로우하고 있는 사용자 목록을 조회
    @Override
    public List<FollowResponse> getFollowers(String email) {
        // 사용자를 팔로우하는 사용자 목록을 조회
        return getFollowList(email, false);
    }

    // 내가 팔로우하고 있는 사용자 목록을 조회
    @Override
    public List<FollowResponse> getFollowings(String email) {
        // 팔로우 중인 사용자 목록을 조회
        return getFollowList(email, true);
    }

    /**
     * 팔로우 목록 또는 팔로워 목록을 조회하고 FollowResponse 리스트로 변환하는 메서드
     *
     * @param email 현재 인증된 사용자 이메일
     * @param isFollowings 팔로잉 목록인지 여부 (true: 팔로잉 목록, false: 팔로워 목록)
     * @return FollowResponse 객체 리스트 반환
     */
    private List<FollowResponse> getFollowList(String email, boolean isFollowings) {
        // 사용자 정보 조회
        User user = userService.findByEmail(email);

        // 팔로우 목록 또는 팔로워 목록을 조회
        List<Follow> followList = isFollowings
                ? followRepository.findByFollower(user) // 팔로잉 목록 조회 (내가 팔로우하는 사람들)
                : followRepository.findByFollowing(user); // 팔로워 목록 조회 (나를 팔로우하는 사람들)

        // Follow 엔티티 리스트를 FollowUserDTO 리스트로 변환하여 반환
        return followList.stream()
                .map(follow -> convertToFollowResponse(follow, isFollowings, user.getId())) // DTO 변환
                .sorted(Comparator.comparing(FollowResponse::getNickname)) // 닉네임 기준으로 정렬
                .collect(Collectors.toList()); // 리스트로 수집하여 반환
    }

    /**
     * Follow 엔티티를 FollowResponse로 변환하는 메서드
     *
     * @param follow Follow 엔티티
     * @param isFollowings 팔로우 목록인지 여부
     * @param currentUserId 현재 인증된 사용자 ID
     * @return FollowResponse 객체 반환
     */
    private FollowResponse convertToFollowResponse(Follow follow, boolean isFollowings, Long currentUserId) {
        // 타겟 유저를 결정: 팔로우 목록일 경우 수신자, 팔로워 목록일 경우 발신자
        User targetUser = isFollowings ? follow.getFollowing() : follow.getFollower();

        // 현재 사용자가 해당 타겟 유저를 팔로우하고 있는지 여부를 확인
        boolean isFollowing = isFollowings || isFollowing(currentUserId, targetUser.getId());

        // FollowUserDTO 객체 생성 및 반환
        return FollowResponse.builder()
                .userId(targetUser.getId()) // 타겟 유저의 ID 설정
                .nickname(targetUser.getNickname()) // 타겟 유저의 닉네임 설정
                .profileImageUrl(targetUser.getProfileImageUrl()) // 타겟 유저의 프로필 이미지 URL 설정
                .following(isFollowing) // 팔로우 여부 설정
                .build();
    }

    // 특정 사용자가 타켓 사용자를 팔로우하고 있는지 여부
    @Override
    public boolean isFollowing(Long userId, Long targetId) {
        return followRepository.existsByFollowerIdAndFollowingId(userId, targetId);
    }

    /**
     * 팔로우 관계를 조회하고 없을 경우 예외를 발생시킵니다.
     *
     * @param followerId 팔로우 요청자 ID
     * @param followingId 팔로우 대상자 ID
     * @return Follow 객체
     */
    private Follow getFollowRelationship(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> {
                    log.warn("언팔로우 요청 실패: 팔로워 ID '{}'가 팔로이 ID '{}'를 팔로우하고 있지 않습니다.", followerId, followingId);
                    return new CustomException(ErrorCode.NOT_FOLLOWING);
                });
    }

}
