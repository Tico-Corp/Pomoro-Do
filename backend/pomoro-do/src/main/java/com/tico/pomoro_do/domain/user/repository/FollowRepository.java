package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 팔로우 중인지 체크
    boolean existsBySenderIdAndReceiverId(Long senderId, Long receiverId);

    // 내가 팔로우하고 있는 사용자 목록을 조회
    List<Follow> findBySender(User sender);

    // 내가 팔로우하는 사람의 수
    int countBySender(User sender);

    // 나를 팔로우하는 사람의 수
    int countByReceiver(User receiver);



}
