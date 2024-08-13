package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.request.AdminDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
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

    private final UserRepository userRepository;
    private final AuthService authService;
    private final ImageService imageService;

    private static final String ADMIN_EMAIL_DOMAIN = "pomorodo.shop";

    /**
     * 관리자 회원가입 처리
     *
     * @param adminDTO AdminDTO 객체
     * @param profileImage 프로필 이미지
     * @return 성공 시 새 Access, Refresh 토큰을 포함하는 TokenDTO
     * @throws CustomException 이메일 도메인이 유효하지 않거나 이미 등록된 사용자인 경우 예외를 던집니다.
     */
    @Override
    @Transactional
    public TokenDTO adminJoin(AdminDTO adminDTO, MultipartFile profileImage) {
        String username = adminDTO.getUsername();
        String nickname = adminDTO.getNickname();

        // 관리자 회원가입 도메인 가져오기
        String domain = getEmailDomain(username);
        // 관리자 회원가입 이메일 도메인 검증
        validateAdminEmailDomain(domain);
        // 관리자 이메일 가입 여부 검증
        checkUserExistence(username);
        // profileImage url 가져오기
        String profileImageUrl = imageService.imageUpload(profileImage, S3Folder.PROFILES.getFolderName());

        // 관리자 생성하기
        User admin = authService.createUser(username, nickname, profileImageUrl, UserRole.ADMIN);

        // 역할
        String role = String.valueOf(UserRole.ADMIN);
        // 관리자 고유 번호: UUID 기반 + 역할명
        String deviceId = UUID.nameUUIDFromBytes(username.getBytes()).toString() + "_" + role;

        return authService.generateAndStoreTokens(username, String.valueOf(UserRole.ADMIN), deviceId);
    }

    /**
     * 관리자 로그인 처리
     *
     * @param adminDTO AdminDTO 객체
     * @return 성공 시 새 Access, Refresh 토큰을 포함하는 TokenDTO
     * @throws CustomException 이메일 도메인이 유효하지 않거나 관리자가 아닌 경우 예외를 던집니다.
     */
    @Override
    @Transactional
    public TokenDTO adminLogin(AdminDTO adminDTO){
        String username = adminDTO.getUsername();
        String nickname = adminDTO.getNickname();

        // 로그인 도메인 가져오기
        String domain = getEmailDomain(username);
        // 로그인 이메일 검증 (관리자 도메인)
        validateAdminEmailDomain(domain);
        // 관리자 로그인 검증
        validateAdminUser(username, nickname);
        // 역할
        String role = String.valueOf(UserRole.ADMIN);
        // 관리자 고유 번호: UUID 기반 + 역할명
        String deviceId = UUID.nameUUIDFromBytes(username.getBytes()).toString() + "_" + role;
        return authService.generateAndStoreTokens(username, role, deviceId);
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
     * 사용자가 이미 존재하는지 확인
     *
     * @param username 사용자 이름
     * @throws CustomException 이미 등록된 사용자인 경우 예외 발생
     */
    private void checkUserExistence(String username) {
        if (userRepository.existsByUsername(username)) {
            log.error("이미 등록된 사용자: {}", username);
            throw new CustomException(ErrorCode.USER_ALREADY_REGISTERED);
        }
    }

    /**
     * 관리자 검증
     *
     * @param username 사용자 이름
     * @param nickname 사용자 닉네임
     * @throws CustomException 사용자가 존재하지 않거나 관리자가 아닌 경우 예외 발생
     */
    private void validateAdminUser(String username, String nickname) {
        Optional<User> userData = userRepository.findByUsername(username);
        if (userData.isEmpty()) {
            log.error("사용자를 찾을 수 없음: {}", username);
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        User admin = userData.get();
        if (!admin.getRole().equals(UserRole.ADMIN)) {
            log.error("관리자 권한 없음: {}", username);
            throw new CustomException(ErrorCode.NOT_AN_ADMIN);
        }
        if (!admin.getNickname().equals(nickname)) {
            log.error("닉네임 불일치: {}", username);
            throw new CustomException(ErrorCode.ADMIN_LOGIN_FAILED);
        }
    }
}
