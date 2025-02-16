package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //email을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);
    boolean existsById(Long id);

    void deleteByEmail(String email);
    void deleteById(Long id);

}
