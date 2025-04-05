package com.tico.pomoro_do.domain.auth.repository;

import com.tico.pomoro_do.domain.auth.entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
}
