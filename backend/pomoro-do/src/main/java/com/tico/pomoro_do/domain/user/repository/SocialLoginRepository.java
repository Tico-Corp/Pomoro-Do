package com.tico.pomoro_do.domain.user.repository;

import com.tico.pomoro_do.domain.user.entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
}
