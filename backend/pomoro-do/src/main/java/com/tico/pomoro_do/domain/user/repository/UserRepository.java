package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.Account;
import com.tico.pomoro_do.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    User findByUsername(String username);

}
