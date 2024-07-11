package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}
