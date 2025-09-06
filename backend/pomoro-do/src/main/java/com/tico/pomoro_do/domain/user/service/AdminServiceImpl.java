package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.auth.service.AuthService;
import com.tico.pomoro_do.domain.auth.service.TokenService;
import com.tico.pomoro_do.domain.user.dto.request.AdminLoginRequest;
import com.tico.pomoro_do.domain.user.dto.request.AdminRegisterRequest;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.policy.InternalEmailPolicy;
import com.tico.pomoro_do.global.exception.ErrorCode;
import com.tico.pomoro_do.global.enums.S3Folder;
import com.tico.pomoro_do.domain.user.enums.UserRole;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
    private final InternalEmailPolicy emailPolicy;

    @Value("${app.auth.internal-email-domain}")
    private String internalEmailDomain;

    // 관리자 회원가입
    @Override
    @Transactional
    public TokenResponse registerAdmin(AdminRegisterRequest adminRegisterRequest, MultipartFile profileImage) {
        // 1) 이메일 정규화
        String email = emailPolicy.normalize(adminRegisterRequest.getEmail());

        // 2) 내부 도메인 검증
        emailPolicy.validateInternalOnly(email);

        // 3) 관리자 이메일 사용 여부 검증
        userService.verifyEmailNotRegistered(email);

        // 4) profileImage URL 가져오기
        String profileImageUrl;
        if (profileImage != null) {
            profileImageUrl = imageService.imageUpload(profileImage, S3Folder.PROFILES.getFolderName());
        } else {
            profileImageUrl = null;
        }

        // 5) 관리자 생성하기
        User admin = authService.createUser(email, adminRegisterRequest.getNickname(), profileImageUrl, UserRole.ADMIN);

        // 역할
        String role = String.valueOf(UserRole.ADMIN);
        // 6) 관리자 고유 번호: UUID 기반 + 역할명
        String deviceId = UUID.nameUUIDFromBytes(email.getBytes()).toString() + "_" + role;

        return tokenService.createAuthTokens(admin.getId(), email, String.valueOf(UserRole.ADMIN), deviceId);
    }

    // 관리자 로그인
    /** 관리자 로그인 (비밀번호 대신 닉네임 일치 검증) */
    @Override
    @Transactional
    public TokenResponse loginAdmin(AdminLoginRequest adminLoginRequest){
        // 1) 이메일 정규화
        String email = emailPolicy.normalize(adminLoginRequest.getEmail());

        // 2) 내부 도메인 검증
        emailPolicy.validateInternalOnly(email);

        // 3) 관리자 로그인 검증 (닉네임 일치 여부)
        User admin = validateAdminUser(email, adminLoginRequest.getNickname());

        // 역할
        String role = String.valueOf(UserRole.ADMIN);
        // 4) 관리자 고유 번호: UUID 기반 + 역할명
        String deviceId = UUID.nameUUIDFromBytes(email.getBytes()).toString() + "_" + role;
        return tokenService.createAuthTokens(admin.getId(), email, role, deviceId);
    }

    /**
     * 관리자 검증
     *
     * @param email 사용자 이메일
     * @param nickname 사용자 닉네임
     * @throws CustomException 사용자가 존재하지 않거나 관리자가 아닌 경우 예외 발생
     */
    private User validateAdminUser(String email, String nickname) {
        User admin = userService.findUserByEmail(email);

        if (!admin.getRole().equals(UserRole.ADMIN)) {
            log.error("관리자 권한 없음: {}", email);
            throw new CustomException(ErrorCode.NOT_AN_ADMIN);
        }
        if (!admin.getNickname().equals(nickname)) {
            log.error("닉네임 불일치: {}", email);
            throw new CustomException(ErrorCode.ADMIN_LOGIN_FAILED);
        }
        return admin;
    }
}
