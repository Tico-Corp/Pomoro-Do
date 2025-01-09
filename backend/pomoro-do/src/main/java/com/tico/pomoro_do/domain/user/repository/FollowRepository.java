package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    // 특정 사용자를 팔로우 중인지 체크
    boolean existsByFollowerIdAndFollowingId(Long currentUserId, Long targetUserId);

    // 내가 팔로우하고 있는 사용자 목록을 조회
    List<Follow> findByFollower(User follower);

    // 나를 팔로우하고 있는 사용자 목록을 조회
    List<Follow> findByFollowing(User following);

    // 내가 팔로우하는 사람의 수
    int countByFollower(User follower);

    // 나를 팔로우하는 사람의 수
    int countByFollowing(User following);

    // 팔로우 엔티티 찾기
    Optional<Follow> findByFollowerIdAndFollowingId(Long currentUserId, Long targetUserId);
}
