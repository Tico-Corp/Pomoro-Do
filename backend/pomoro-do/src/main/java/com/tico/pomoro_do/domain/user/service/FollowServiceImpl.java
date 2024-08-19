package com.tico.pomoro_do.domain.user.service;

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
        if (followRepository.existsBySenderAndReceiver(sender, receiver)) {
            log.warn("사용자 '{}'가 이미 사용자 ID '{}'를 팔로우했습니다.", senderUsername, receiverId);
            throw new CustomException(ErrorCode.ALREADY_FOLLOWED);
        }

        Follow follow = Follow.builder()
                .sender(sender)
                .receiver(receiver)
                .build();

        followRepository.save(follow);
    }
}
