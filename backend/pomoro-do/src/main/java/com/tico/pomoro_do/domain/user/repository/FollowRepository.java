package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Follow;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    boolean existsBySenderAndReceiver(User sender, User receiver);
}
