package com.tico.pomoro_do.domain.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.domain.auth.dto.GoogleUserInfo;
import com.tico.pomoro_do.domain.user.entity.SocialLogin;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.SocialLoginRepository;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.domain.user.service.ImageService;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.common.constants.CategoryConstants;
import com.tico.pomoro_do.global.enums.*;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.util.DateUtils;
import com.tico.pomoro_do.global.util.ValidationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;


@Service
@Transactional(readOnly = true) // (성능 최적화 - 읽기 전용에만 사용)
@RequiredArgsConstructor // 파이널 필드만 가지고 생성사 주입 함수 만듬 (따로 작성할 필요 없다.)
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${google.clientId}")
    private String clientId;

    //jwt관리 및 검증 utill
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final CategoryService categoryService;


    // 구글 ID 토큰 무결성 검사
    @Override
    public GoogleUserInfo verifyGoogleIdToken(String idToken) throws GeneralSecurityException, IOException, IllegalArgumentException {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken); // 검증 실패시 IllegalArgumentException를 던짐
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return GoogleUserInfo.builder()
                    .userId(payload.getSubject())
                    .email(payload.getEmail())
                    .name((String) payload.get("name"))
                    .pictureUrl((String) payload.get("picture"))
                    .build();
        } else {
            log.error("구글 ID 토큰 검증 실패: 토큰이 유효하지 않음");
            throw new CustomException(ErrorCode.GOOGLE_TOKEN_VERIFICATION_FAILED);
        }
    }

    // 구글 로그인
    @Override
    @Transactional
    public TokenResponse googleLogin(String idTokenHeader, String deviceId) throws GeneralSecurityException, IOException {
        // deviceId 유효성 검사
        ValidationUtils.validateDeviceId(deviceId);
        // 토큰 추출
        String idToken = jwtUtil.extractToken(idTokenHeader, TokenType.GOOGLE);
        // 구글 토큰 유효성 검증
        GoogleUserInfo userInfo = verifyGoogleIdToken(idToken);
        // 회원 가입 여부 판단 -> 회원 가입 x -> 에러 발생
        validateUserExists(userInfo.getEmail());
        // 회원 가입되어 있으면 토큰 발급
        return tokenService.createAuthTokens(userInfo.getEmail(), String.valueOf(UserRole.USER), deviceId);
    }

    // 구글 회원가입
    @Override
    @Transactional
    public TokenResponse googleJoin(String idTokenHeader, String deviceId, String nickname, MultipartFile profileImage, ProfileImageType imageType) throws GeneralSecurityException, IOException {

        // 입력값 유효성 검사
        joinValidateInputs(deviceId, nickname);

        // 구글 idToken 유효성 검사 및 추출
        String idToken = jwtUtil.extractToken(idTokenHeader, TokenType.GOOGLE);
        // 구글 id 토큰 검증
        GoogleUserInfo userInfo = verifyGoogleIdToken(idToken);
        // 사용자의 이메일 중복 체크
        checkIfUserAlreadyRegistered(userInfo.getEmail());

        // 알맞는 profileImage url 가져오기 (null 가능)
        String profileImageUrl = determineProfileImageUrl(imageType, profileImage, userInfo);
        // 사용자 정보 저장
        User user = createUser(userInfo.getEmail(), nickname, profileImageUrl, UserRole.USER);
        // 소셜 로그인 정보 저장
        createSocialLogin(user, userInfo.getUserId());

        // 기본 카테고리 생성
        Category category = categoryService.createNewCategory(
                user,
                DateUtils.getCurrentDate(),
                CategoryConstants.DEFAULT_CATEGORY_TITLE,
                CategoryConstants.DEFAULT_CATEGORY_COLOR,
                CategoryConstants.DEFAULT_CATEGORY_VISIBILITY,
                CategoryConstants.DEFAULT_CATEGORY_TYPE
        );

        log.info("구글 회원가입 성공: 이메일 = {}", userInfo.getEmail());
        return tokenService.createAuthTokens(userInfo.getEmail(), String.valueOf(UserRole.USER), deviceId);
    }

    /**
     * 회원가입 입력값 검증 메서드
     *
     * @param deviceId 기기 고유 번호
     * @param nickname 닉네임
     */
    private void joinValidateInputs(String deviceId, String nickname) {
        ValidationUtils.validateDeviceId(deviceId);
        ValidationUtils.validateNickname(nickname);
    }

    /**
     * 사용자 등록 여부 확인 메서드
     *
     * @param email 사용자 이메일
     */
    private void validateUserExists(String email) {
        if (!userRepository.existsByEmail(email)) {
            log.error("사용자 등록되지 않음: 이메일 = {}", email);
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    /**
     * 사용자가 이미 등록되어 있는지 확인합니다.
     *
     * @param email 사용자의 이메일
     */
    private void checkIfUserAlreadyRegistered(String email) {
        if (userRepository.existsByEmail(email)) {
            log.error("이미 등록된 사용자: 이메일 = {}", email);
            throw new CustomException(ErrorCode.USER_ALREADY_REGISTERED);
        }
    }

    /**
     * 프로필 이미지 URL을 결정합니다.
     *
     * @param imageType 프로필 이미지의 유형 (FILE, GOOGLE, 또는 DEFAULT)
     * @param profileImage 사용자가 업로드한 프로필 이미지 파일 (FILE 유형일 때 사용)
     * @param userInfo 구글 사용자 정보 DTO (GOOGLE 유형일 때 사용)
     * @return 프로필 이미지의 URL. FILE 유형인 경우 업로드된 이미지의 URL을,
     *         GOOGLE 유형인 경우 구글 프로필 이미지 URL을 반환하며,
     *         DEFAULT 유형인 경우 null을 반환합니다.
     */
    private String determineProfileImageUrl(ProfileImageType imageType, MultipartFile profileImage, GoogleUserInfo userInfo) {
        return switch (imageType) {
            case FILE -> imageService.imageUpload(profileImage, S3Folder.PROFILES.getFolderName());
            case GOOGLE -> userInfo.getPictureUrl();
            // DEFAULT 타입일 때 default로 통합하여 처리
            default -> null;
        };
    }

    // User 생성
    @Override
    public User createUser(String email, String nickname, String profileImageUrl, UserRole role) {

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(role)
                .build();
        userRepository.save(user);

        log.info("사용자 저장 성공: 이메일 = {}", email);
        return user;
    }

    /**
     * 소셜 로그인 정보를 저장합니다.
     *
     * @param user       사용자 객체
     * @param providerId 소셜 로그인 제공자 ID
     */
    private void createSocialLogin(User user, String providerId) {
        // 추후에 소셜 로그인이 추가된다면, provider을 변수로 받는다.
        SocialLogin socialLogin = SocialLogin.builder()
                .user(user)
                .provider(SocialProvider.GOOGLE)
                .providerId(providerId)
                .build();

        socialLoginRepository.save(socialLogin);

        log.info("소셜 로그인 정보 저장 성공: 소셜 ID = {}", providerId);
    }

    // Refresh 토큰으로 Access토큰 발급
    @Transactional
    @Override
    public TokenResponse reissueToken(String deviceId, String refreshHeader) {
        // 검증 로직
        String refresh = tokenService.validateAndGetRefreshToken(refreshHeader, deviceId);

        // 생성 로직
        // 리프레시 토큰에서 사용자 정보를 추출합니다.
        String email = jwtUtil.getEmail(refresh);
        String role = jwtUtil.getRole(refresh);
        // 토큰 재발행
        return tokenService.createAuthTokens(email, role, deviceId);

    }
}
