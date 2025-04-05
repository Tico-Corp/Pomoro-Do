package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.service.TokenService;
import com.tico.pomoro_do.domain.user.dto.response.UserProfileResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailResponse;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.FollowRepository;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.exception.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;
    final private FollowRepository followRepository;
    final private TokenService tokenService;

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("[User] 사용자 조회 실패: userId={}", userId);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

    // 여러 사용자 ID에 해당하는 사용자 정보를 한 번에 조회
    @Override
    public Map<Long, User> findUsersByIds(Set<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        return users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("[User] 사용자 조회 실패: email={}", email);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

    @Override
    public UserDetailResponse getUserDetail(Long userId) {
        User user = findUserById(userId);
        // 내가 팔로우하는 사람의 수 계산
        int followingCount = followRepository.countByFollower(user);
        // 나를 팔로우하는 사람의 수 계산
        int followerCount = followRepository.countByFollowing(user);

        log.info("[User] 내 프로필 조회 성공: userId={}", userId);

        return UserDetailResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .followingCount(followingCount)
                .followerCount(followerCount)
                .build();
    }

    @Override
    public UserProfileResponse getUserProfile(Long currentUserId, Long targetUserId) {

        // 주어진 userId에 해당하는 특정 사용자 정보 조회
        User targetUser = findUserById(targetUserId);
        // 현재 사용자가 해당 특정 사용자를 팔로우하고 있는지 여부 확인
        boolean isFollowing = followRepository.existsByFollowerIdAndFollowingId(currentUserId, targetUserId);

        log.info("[User] 특정 사용자 프로필 조회: currentUserId={}, targetUserId={}", currentUserId, targetUserId);

        // 사용자 정보 및 팔로우 상태를 포함한 DTO 반환
        return UserProfileResponse.builder()
                .userId(targetUser.getId())
                .nickname(targetUser.getNickname())
                .profileImageUrl(targetUser.getProfileImageUrl())
                .following(isFollowing)
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId, String deviceId, String refreshHeader) {
        log.info("[User] 사용자 삭제 요청: userId={}, deviceId={}", userId, deviceId);

        // 회원 토큰 검증
        tokenService.validateRefreshTokenDetails(refreshHeader, deviceId, userId);
        // 해당 회원의 모든 리프레시 토큰 삭제
        tokenService.deleteAllRefreshTokensByUserId(userId);
        // 해당 유저 삭제
        userRepository.deleteById(userId);
        log.info("[User] 사용자 삭제 완료: userId={}", userId);

    }

    // 이메일 중복 검증
    @Override
    public void verifyEmailNotRegistered(String email) {
        if (userRepository.existsByEmail(email)) {
            log.warn("[User] 이메일 중복됨: email={}", email);
            throw new CustomException(ErrorCode.USER_ALREADY_REGISTERED);
        }
        log.info("[User] 이메일 사용 가능: email={}", email);
    }

    // 이메일 존재 여부 검증
    @Override
    public void verifyEmailExists(String email) {
        if (!userRepository.existsByEmail(email)) {
            log.error("[User] 존재하지 않는 이메일: email={}", email);
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        log.info("[User] 이메일 존재 확인 완료: email={}", email);
    }

    // 이메일이 사용 가능한 지 검증
    @Override
    public void verifyUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            log.error("[User] 존재하지 않는 사용자: userId={}", userId);
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        log.info("[User] 사용자 존재 확인 완료: userId={}", userId);
    }
}
