package com.tico.pomoro_do.domain.user.policy;

import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 관리자/더미 계정은 외부와 섞이지 않게 내부 전용 도메인만 허용합니다.
 * RFC 2606 예약 TLD(.invalid) 사용 → 실제 발송/수신 불가, 안전한 테스트에 적합
 */
@Slf4j
@Component
public class InternalEmailPolicy {

    @Value("${app.auth.internal-email-domain}")
    private String internalEmailDomain;

    /** 이메일 정규화(공백 제거, 소문자) */
    public String normalize(String email) {
        return email == null ? null : email.trim().toLowerCase(Locale.ROOT);
    }

    /** 내부 전용 도메인(@pomorodo.invalid) 여부 확인 */
    public boolean isInternalDomain(String email) {
        if (email == null) return false;
        String e = normalize(email);
        return e.endsWith("@" + internalEmailDomain);
    }

    /** 관리자/내부 계정만 생성/로그인 허용 */
    public void validateInternalOnly(String email) {
        if (!isInternalDomain(email)) {
            log.warn("관리자 로그인 시도: 허용되지 않은 이메일 {}", email);
            throw new CustomException(ErrorCode.INVALID_ADMIN_EMAIL);
        }
    }
}
