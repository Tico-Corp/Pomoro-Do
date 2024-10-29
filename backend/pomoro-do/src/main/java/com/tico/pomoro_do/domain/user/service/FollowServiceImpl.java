package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.FollowUserDTO;
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

    /**
     * 특정 사용자 팔로우
     *
     * @param senderUsername 팔로우하는 사용자 이름
     * @param receiverId   팔로우될 사용자 ID
     */
    @Override
    @Transactional
    public void follow(String senderUsername, Long receiverId) {
        // 유효성 검사 (null 또는 음수 체크)
        ValidationUtils.validateUserId(receiverId);

        User sender = userService.findByUsername(senderUsername);
        User receiver = userService.findByUserId(receiverId);

        // 본인을 팔로우 하는 지 확인
        if (sender.equals(receiver)) {
            log.warn("사용자 '{}'가 본인을 팔로우하려고 시도했습니다.", senderUsername);
            throw new CustomException(ErrorCode.SELF_FOLLOW_NOT_ALLOWED);
        }

        // 이미 팔로우 중인지 확인
        if (isFollowedByUser(sender.getId(), receiverId)) {
            log.warn("사용자 '{}'가 이미 사용자 ID '{}'를 팔로우했습니다.", senderUsername, receiverId);
            throw new CustomException(ErrorCode.ALREADY_FOLLOWED);
        }

        Follow follow = Follow.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        followRepository.save(follow);
    }

    /**
     * 사용자가 팔로우 중인 사용자 목록을 조회하는 서비스 메서드
     *
     * @param username 현재 인증된 사용자의 사용자 이름
     * @return FollowUserDTO 객체 리스트 반환
     */
    @Override
    public List<FollowUserDTO> getFollowingList(String username) {
        // 팔로우 중인 사용자 목록을 조회
        return getFollowList(username, true);
    }

    /**
     * 사용자를 팔로우 중인 사용자 목록을 조회하는 서비스 메서드
     *
     * @param username 현재 인증된 사용자의 사용자 이름
     * @return FollowUserDTO 객체 리스트 반환
     */
    @Override
    public List<FollowUserDTO> getFollowersList(String username) {
        // 사용자를 팔로우하는 사용자 목록을 조회
        return getFollowList(username, false);
    }

    /**
     * 팔로우 목록 또는 팔로워 목록을 조회하고 DTO 리스트로 변환하는 메서드
     *
     * @param username 현재 인증된 사용자의 사용자 이름
     * @param isFollowingList 팔로우 목록인지 여부 (true: 팔로우 목록, false: 팔로워 목록)
     * @return FollowUserDTO 객체 리스트 반환
     */
    private List<FollowUserDTO> getFollowList(String username, boolean isFollowingList) {
        // 사용자 정보 조회
        User user = userService.findByUsername(username);

        // 팔로우 목록 또는 팔로워 목록을 조회
        List<Follow> followList = isFollowingList
                ? followRepository.findBySender(user) // 팔로우 목록(팔로잉) 조회
                : followRepository.findByReceiver(user); // 팔로워 목록 조회

        // Follow 엔티티 리스트를 FollowUserDTO 리스트로 변환하여 반환
        return followList.stream()
                .map(follow -> convertToFollowUserDTO(follow, isFollowingList, user.getId())) // DTO 변환
                .sorted(Comparator.comparing(FollowUserDTO::getNickname)) // 닉네임 기준으로 정렬
                .collect(Collectors.toList()); // 리스트로 수집하여 반환
    }

    /**
     * Follow 엔티티를 FollowUserDTO로 변환하는 메서드
     *
     * @param follow Follow 엔티티
     * @param isFollowingList 팔로우 목록인지 여부
     * @param currentUserId 현재 인증된 사용자의 ID
     * @return FollowUserDTO 객체 반환
     */
    private FollowUserDTO convertToFollowUserDTO(Follow follow, boolean isFollowingList, Long currentUserId) {
        // 타겟 유저를 결정: 팔로우 목록일 경우 수신자, 팔로워 목록일 경우 발신자
        User targetUser = isFollowingList ? follow.getReceiver() : follow.getSender();

        // 현재 사용자가 해당 타겟 유저를 팔로우하고 있는지 여부를 확인
        boolean isFollowing = isFollowingList || isFollowedByUser(currentUserId, targetUser.getId());

        // FollowUserDTO 객체 생성 및 반환
        return FollowUserDTO.builder()
                .userId(targetUser.getId()) // 타겟 유저의 ID 설정
                .nickname(targetUser.getNickname()) // 타겟 유저의 닉네임 설정
                .profileImageUrl(targetUser.getProfileImageUrl()) // 타겟 유저의 프로필 이미지 URL 설정
                .following(isFollowing) // 팔로우 여부 설정
                .build();
    }

    /**
     * 현재 사용자가 특정 사용자를 팔로우하고 있는지 여부를 확인하는 메서드
     *
     * @param currentUserId 현재 인증된 사용자의 ID
     * @param targetUserId 팔로우 여부를 확인할 대상 사용자의 ID
     * @return 팔로우 여부 반환
     */
    @Override
    public boolean isFollowedByUser(Long currentUserId, Long targetUserId) {
        return followRepository.existsBySenderIdAndReceiverId(currentUserId, targetUserId);
    }

    /**
     * 특정 사용자 팔로우 취소
     *
     * @param senderUsername 현재 사용자 이름
     * @param receiverId   팔로우 취소하는 사용자 ID
     */
    @Override
    @Transactional
    public void unfollow(String senderUsername, Long receiverId) {
        // 유효성 검사 (null 또는 음수 체크)
        ValidationUtils.validateUserId(receiverId);

        // 팔로워 유저 조회
        User sender = userService.findByUsername(senderUsername);
        // 팔로이 유저 조회
        User receiver = userService.findByUserId(receiverId);

        // 팔로우 관계 조회 및 확인
        Follow follow = findFollowRelationship(sender.getId(), receiverId);

        // 팔로우 관계 삭제
        followRepository.delete(follow);
    }

    /**
     * 팔로우 관계를 조회하고 없을 경우 예외를 발생시킵니다.
     *
     * @param senderId 팔로워의 ID
     * @param receiverId 팔로이의 ID
     * @return Follow 객체
     */
    private Follow findFollowRelationship(Long senderId, Long receiverId) {
        return followRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> {
                    log.warn("언팔로우 요청 실패: 팔로워 ID '{}'가 팔로이 ID '{}'를 팔로우하고 있지 않습니다.", senderId, receiverId);
                    return new CustomException(ErrorCode.NOT_FOLLOWING);
                });
    }

}
