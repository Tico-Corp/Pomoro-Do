package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.service.TokenService;
import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailResponse;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.FollowRepository;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;
    final private FollowRepository followRepository;
    final private TokenService tokenService;

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없음: {}", userId);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없음: {}", email);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

    @Override
    public UserDetailResponse getMyDetail(String email) {
        User user = findByEmail(email);

        // 내가 팔로우하는 사람의 수 계산
        int followingCount = followRepository.countByFollower(user);
        // 나를 팔로우하는 사람의 수 계산
        int followerCount = followRepository.countByFollowing(user);

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
    public FollowResponse getUserDetail(String email, Long userId) {

        // 주어진 email에 해당하는 현재 사용자 ID 조회
        Long myUserId = findByEmail(email).getId();
        // 주어진 userId에 해당하는 특정 사용자 정보 조회
        User user = findByUserId(userId);

        // 현재 사용자가 해당 특정 사용자를 팔로우하고 있는지 여부 확인
        boolean isFollowing = followRepository.existsByFollowerIdAndFollowingId(myUserId, userId);

        // 사용자 정보 및 팔로우 상태를 포함한 DTO 반환
        return FollowResponse.builder()
                .userId(user.getId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .following(isFollowing)
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(String email, String deviceId, String refreshHeader) {
        // 회원 토큰 검증
        tokenService.validateRefreshTokenDetails(refreshHeader, deviceId, email);
        // 해당 회원의 모든 리프레시 토큰 삭제
        tokenService.deleteAllRefreshTokensByEmail(email);
        // 해당 유저 삭제
        userRepository.deleteByEmail(email);
    }

    // 이메일이 등록되어 있는 지 검증
    @Override
    public void isEmailRegistered(String email) {
        if (userRepository.existsByEmail(email)) {
            log.error("이미 등록된 사용자: {}", email);
            throw new CustomException(ErrorCode.USER_ALREADY_REGISTERED);
        }
    }

    // 이메일이 사용 가능한 지 검증
    @Override
    public void validateEmailExists(String email) {
        if (!userRepository.existsByEmail(email)) {
            log.error("등록되지 않은 사용자: 이메일 = {}", email);
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
