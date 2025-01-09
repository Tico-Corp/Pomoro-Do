package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.service.TokenService;
import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;
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
    public UserDetailDTO getMyDetail(String username) {
        User user = findByUsername(username);

        // 내가 팔로우하는 사람의 수 계산
        int followingCount = followRepository.countByFollower(user);
        // 나를 팔로우하는 사람의 수 계산
        int followerCount = followRepository.countByFollowing(user);

        return UserDetailDTO.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .followingCount(followingCount)
                .followerCount(followerCount)
                .build();
    }

    @Override
    public FollowResponse getUserDetail(String username, Long userId) {

        // 주어진 username에 해당하는 현재 사용자 ID 조회
        Long myUserId = findByUsername(username).getId();
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
    public void deleteUser(String username, String deviceId, String refreshHeader) {
        // 회원 토큰 검증
        tokenService.validateRefreshTokenDetails(refreshHeader, deviceId, username);
        // 해당 회원의 모든 리프레시 토큰 삭제
        tokenService.deleteAllRefreshTokensByUsername(username);
        // 해당 유저 삭제
        userRepository.deleteByUsername(username);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없음: {}", username);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

    @Override
    public User findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("사용자를 찾을 수 없음: {}", userId);
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
    }

}
