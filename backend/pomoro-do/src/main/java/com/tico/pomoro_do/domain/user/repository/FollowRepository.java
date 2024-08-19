package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 이미 팔로우 중인지 체크
    boolean existsBySenderAndReceiver(User sender, User receiver);

    // 내가 팔로우하고 있는 사용자 목록을 조회
    List<Follow> findBySender(User sender);

}
