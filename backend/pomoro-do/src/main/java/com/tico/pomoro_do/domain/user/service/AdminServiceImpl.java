package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.auth.service.AuthService;
import com.tico.pomoro_do.domain.auth.service.TokenService;
import com.tico.pomoro_do.domain.user.dto.request.AdminRequest;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.enums.S3Folder;
import com.tico.pomoro_do.global.enums.UserRole;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true) // (성능 최적화 - 읽기 전용에만 사용)
@RequiredArgsConstructor // 파이널 필드만 가지고 생성사 주입 함수 만듬 (따로 작성할 필요 없다.)
@Slf4j
public class AdminServiceImpl implements AdminService {

    private final UserService userService;
    private final AuthService authService;
    private final TokenService tokenService;
    private final ImageService imageService;

    private static final String ADMIN_EMAIL_DOMAIN = "pomorodo.shop";

    // 관리자 회원가입
    @Override
    @Transactional
    public TokenResponse adminJoin(AdminRequest adminRequest, MultipartFile profileImage) {
        String email = adminRequest.getEmail();
        String nickname = adminRequest.getNickname();

        // 관리자 회원가입 도메인 가져오기
        String domain = getEmailDomain(email);
        // 관리자 회원가입 이메일 도메인 검증
        validateAdminEmailDomain(domain);
        // 관리자 이메일 가입 여부 검증
        userService.isEmailRegistered(email);

        // profileImage URL 가져오기
        String profileImageUrl;
        if (profileImage != null) {
            profileImageUrl = imageService.imageUpload(profileImage, S3Folder.PROFILES.getFolderName());
        } else {
            profileImageUrl = null;
        }

        // 관리자 생성하기
        User admin = authService.createUser(email, nickname, profileImageUrl, UserRole.ADMIN);

        // 역할
        String role = String.valueOf(UserRole.ADMIN);
        // 관리자 고유 번호: UUID 기반 + 역할명
        String deviceId = UUID.nameUUIDFromBytes(email.getBytes()).toString() + "_" + role;

        return tokenService.createAuthTokens(email, String.valueOf(UserRole.ADMIN), deviceId);
    }

    // 관리자 로그인
    @Override
    @Transactional
    public TokenResponse adminLogin(AdminRequest adminRequest){
        String email = adminRequest.getEmail();
        String nickname = adminRequest.getNickname();

        // 로그인 도메인 가져오기
        String domain = getEmailDomain(email);
        // 로그인 이메일 검증 (관리자 도메인)
        validateAdminEmailDomain(domain);
        // 관리자 로그인 검증
        validateAdminUser(email, nickname);
        // 역할
        String role = String.valueOf(UserRole.ADMIN);
        // 관리자 고유 번호: UUID 기반 + 역할명
        String deviceId = UUID.nameUUIDFromBytes(email.getBytes()).toString() + "_" + role;
        return tokenService.createAuthTokens(email, role, deviceId);
    }

    /**
     * 이메일에서 도메인 부분을 추출
     *
     * @param email 이메일 주소
     * @return 이메일 도메인 부분
     */
    private String getEmailDomain(String email) {
        return email.substring(email.indexOf("@") + 1);
    }

    /**
     * 이메일 도메인 검증
     *
     * @param domain 이메일 도메인
     * @throws CustomException 유효하지 않은 이메일 도메인의 경우 예외 발생
     */
    private void validateAdminEmailDomain(String domain) {
        if (!ADMIN_EMAIL_DOMAIN.equals(domain)) {
            log.error("유효하지 않은 이메일 도메인: {}", domain);
            throw new CustomException(ErrorCode.ADMIN_EMAIL_ONLY);
        }
    }

    /**
     * 관리자 검증
     *
     * @param email 사용자 이메일
     * @param nickname 사용자 닉네임
     * @throws CustomException 사용자가 존재하지 않거나 관리자가 아닌 경우 예외 발생
     */
    private void validateAdminUser(String email, String nickname) {
        User admin = userService.findByEmail(email);

        if (!admin.getRole().equals(UserRole.ADMIN)) {
            log.error("관리자 권한 없음: {}", email);
            throw new CustomException(ErrorCode.NOT_AN_ADMIN);
        }
        if (!admin.getNickname().equals(nickname)) {
            log.error("닉네임 불일치: {}", email);
            throw new CustomException(ErrorCode.ADMIN_LOGIN_FAILED);
        }
    }
}
