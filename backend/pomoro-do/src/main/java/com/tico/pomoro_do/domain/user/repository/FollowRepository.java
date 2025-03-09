package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FollowRepository extends JpaRepository<Follow, Long> {

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

    /**
     * 팔로워와 팔로우 대상으로 팔로우 관계 존재 여부 확인
     *
     * @param followerId 팔로워 ID
     * @param followeeId 팔로우 대상 ID
     * @return 팔로우 관계 존재 여부
     */
    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followeeId);

    // 특정 사용자가 팔로우하는 모든 사용자 ID를 한 번에 조회
    Set<Long> findFollowingIdsByFollowerId(Long followerId);

    /**
     * 팔로워와 여러 팔로우 대상으로 팔로우 관계 목록 조회
     *
     * @param followerId 팔로워 ID
     * @param followeeIds 팔로우 대상 ID 목록
     * @return 팔로우 관계 목록
     */
    List<Follow> findByFollowerIdAndFollowingIdIn(Long followerId, Collection<Long> followeeIds);
}
