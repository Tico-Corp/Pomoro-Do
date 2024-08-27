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

import java.util.Collections;
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
     * 사용자 팔로우 로직
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

    @Override
    public List<FollowUserDTO> getFollowingList(String username) {
        // 사용자 조회
        User user = userService.findByUsername(username);
        // 사용자와 관련된 팔로우 목록을 조회
        List<Follow> followList = followRepository.findBySender(user);

        // 팔로우 리스트가 비어 있으면 빈 리스트 반환
        if (followList.isEmpty()) {
            return Collections.emptyList();
        }

        // FollowUserDTO 리스트 생성 및 변환
        return followList.stream()
                .map(follow -> FollowUserDTO.builder()
                        .userId(follow.getReceiver().getId())
                        .nickname(follow.getReceiver().getNickname())
                        .profileImageUrl(follow.getReceiver().getProfileImageUrl())
                        .following(true)
                        .build())
                .sorted(Comparator.comparing(FollowUserDTO::getNickname)) // 닉네임으로 정렬
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자가 다른 사용자를 팔로우하고 있는지 확인
     *
     * @param senderId 팔로우를 한 사용자의 ID
     * @param receiverId 팔로우 당한 사용자의 ID
     * @return 팔로우 중이면 true, 그렇지 않으면 false
     */
    @Override
    public boolean isFollowedByUser(Long senderId, Long receiverId) {
        return followRepository.existsBySenderIdAndReceiverId(senderId, receiverId);
    }
}
