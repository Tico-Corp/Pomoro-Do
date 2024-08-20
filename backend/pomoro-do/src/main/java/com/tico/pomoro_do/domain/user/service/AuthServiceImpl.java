package com.tico.pomoro_do.domain.user.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.tico.pomoro_do.domain.category.entity.Category;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.entity.Refresh;
import com.tico.pomoro_do.domain.user.entity.SocialLogin;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.RefreshRepository;
import com.tico.pomoro_do.domain.user.repository.SocialLoginRepository;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.common.constants.CategoryConstants;
import com.tico.pomoro_do.global.enums.*;
import com.tico.pomoro_do.global.exception.CustomException;
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

    @Value("${jwt.access-expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;
    private final SocialLoginRepository socialLoginRepository;
    private final RefreshRepository refreshRepository;
    private final TokenService tokenService;
    private final ImageService imageService;
    private final CategoryService categoryService;


    /**
     * 구글 ID 토큰으로 무결성 검증
     *
     * @param idToken 구글 ID 토큰
     * @return 검증된 GoogleUserInfoDTO
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않은 경우 예외
     */
    @Override
    public GoogleUserInfoDTO verifyGoogleIdToken(String idToken) throws GeneralSecurityException, IOException, IllegalArgumentException {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken); // 검증 실패시 IllegalArgumentException를 던짐
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return GoogleUserInfoDTO.builder()
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

    /**
     * 사용자 인증을 위한 액세스 토큰과 리프레시 토큰을 생성하고 저장
     *
     * @param username 사용자 이메일
     * @param role 사용자 역할
     * @param deviceId 기기 고유 번호
     * @return TokenDTO 객체, 생성된 액세스 토큰을 포함
     */
    @Override
    public TokenDTO generateAndStoreTokens(String username, String role, String deviceId) {
        // DB에서 deviceId에 해당하는 기존 리프레시 토큰을 삭제
        refreshRepository.deleteByDeviceId(deviceId);

        // 액세스 토큰 생성
        String accessToken = jwtUtil.createJwt(TokenType.ACCESS.name(), username, role, accessExpiration); // 60분
        // 리프레시 토큰 생성
        String refreshToken = jwtUtil.createJwt(TokenType.REFRESH.name(), username, role, refreshExpiration); // 24시간
        // 리프레시 토큰을 DB에 저장
        tokenService.addRefreshEntity(username, refreshToken, refreshExpiration, deviceId);

        return new TokenDTO(accessToken, refreshToken);
    }

    /**
     * 구글 ID 토큰으로 로그인 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @return TokenDTO를 포함하는 객체
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 등록되지 않은 사용자인 경우 예외
     */
    @Override
    @Transactional
    public TokenDTO googleLogin(String idTokenHeader, String deviceId) throws GeneralSecurityException, IOException {
        // deviceId 유효성 검사
        ValidationUtils.validateDeviceId(deviceId);
        // 토큰 추출
        String idToken = jwtUtil.extractToken(idTokenHeader, TokenType.GOOGLE);
        // 구글 토큰 유효성 검증
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(idToken);
        // 회원 가입 여부 판단
        validateUserExists(userInfo.getEmail());

        return generateAndStoreTokens(userInfo.getEmail(), String.valueOf(UserRole.USER), deviceId);
    }

    /**
     * 구글 ID 토큰으로 회원가입 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @param nickname 닉네임
     * @param profileImage 프로필 이미지
     * @param imageType 프로필 이미지 타입
     * @return TokenDTO를 포함하는 객체
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 이미 등록된 사용자인 경우 예외
     */
    @Override
    @Transactional
    public TokenDTO googleJoin(String idTokenHeader, String deviceId, String nickname, MultipartFile profileImage, ProfileImageType imageType) throws GeneralSecurityException, IOException {

        // 입력값 유효성 검사
        joinValidateInputs(deviceId, nickname);

        // 구글 idToken 유효성 검사 및 추출
        String idToken = jwtUtil.extractToken(idTokenHeader, TokenType.GOOGLE);
        // 구글 id 토큰 검증
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(idToken);
        // 사용자의 이메일 중복 체크
        checkIfUserAlreadyRegistered(userInfo.getEmail());

        // 알맞는 profileImage url 가져오기 (null 가능)
        String profileImageUrl = determineProfileImageUrl(imageType, profileImage, userInfo);
        // 사용자 정보 저장
        User user = createUser(userInfo.getEmail(), nickname, profileImageUrl, UserRole.USER);
        // 소셜 로그인 정보 저장
        saveSocialLogin(user, userInfo.getUserId());

        // 기본 카테고리 생성
        Category category = categoryService.createNewCategory(
                user,
                CategoryConstants.DEFAULT_CATEGORY_TITLE,
                CategoryConstants.DEFAULT_CATEGORY_COLOR,
                CategoryConstants.DEFAULT_CATEGORY_VISIBILITY,
                CategoryConstants.DEFAULT_CATEGORY_TYPE
        );

        log.info("구글 회원가입 성공: 이메일 = {}", userInfo.getEmail());
        return generateAndStoreTokens(userInfo.getEmail(), String.valueOf(UserRole.USER), deviceId);
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
        if (!userRepository.existsByUsername(email)) {
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
        if (userRepository.existsByUsername(email)) {
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
    private String determineProfileImageUrl(ProfileImageType imageType, MultipartFile profileImage, GoogleUserInfoDTO userInfo) {
        switch (imageType) {
            case FILE:
                return imageService.imageUpload(profileImage, S3Folder.PROFILES.getFolderName());
            case GOOGLE:
                return userInfo.getPictureUrl();
            // DEFAULT와 default를 통합하여 간결하게 처리
            default:
                return null;
        }
    }

    /**
     * 새 사용자 생성
     *
     * @param username 사용자 이메일
     * @param nickname 사용자 닉네임
     * @param profileImageUrl 사용자 프로필 이미지 URL
     * @param role 사용자 역할
     * @return 생성된 User 객체
     */
    @Override
    public User createUser(String username, String nickname, String profileImageUrl, UserRole role) {

        User user = User.builder()
                .username(username)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(role)
                .build();
        userRepository.save(user);

        log.info("사용자 저장 성공: 이메일 = {}", username);
        return user;
    }

    /**
     * 소셜 로그인 정보를 저장합니다.
     *
     * @param user     사용자 객체
     * @param socialId 소셜 로그인 ID
     */
    private void saveSocialLogin(User user, String socialId) {

        SocialLogin socialLogin = SocialLogin.builder()
                .user(user)
                .provider(SocialProvider.GOOGLE)
                .socialId(socialId)
                .build();

        socialLoginRepository.save(socialLogin);

        log.info("소셜 로그인 정보 저장 성공: 소셜 ID = {}", socialId);
    }

    /**
     * Refresh 토큰을 사용하여 Access 토큰 재발급
     *
     * @param deviceId 기기 고유 번호
     * @param refreshHeader 리프레시 토큰
     * @return 새 Access 토큰을 포함하는 TokenDTO
     */
    @Transactional
    @Override
    public TokenDTO reissueToken(String deviceId, String refreshHeader) {
        // 헤더를 검증합니다.
        String refresh = jwtUtil.extractToken(refreshHeader, TokenType.REFRESH);

        // 리프레시 토큰을 검증합니다.
        jwtUtil.validateToken(refresh, TokenType.REFRESH);

        // DB에서 리프레시 토큰에 해당하는 리프레시 토큰 정보를 가져옵니다.
        Refresh refreshEntity = tokenService.getRefreshByRefreshToken(refresh);

        // DB에 저장된 deviceId와 요청된 deviceId이 일치하는지 확인합니다.
        if (!refreshEntity.getDeviceId().equals(deviceId)) {
            log.error("Device ID가 DB에 존재하지 않음: deviceId = {}", deviceId);
            throw new CustomException(ErrorCode.DEVICE_ID_MISMATCH);
        }

        // 리프레시 토큰에서 사용자 정보를 추출합니다.
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        // 새로운 액세스 및 리프레시 토큰을 생성합니다.
        String newAccess = jwtUtil.createJwt(TokenType.ACCESS.name(), username, role, accessExpiration); // 60분
        String newRefresh = jwtUtil.createJwt(TokenType.REFRESH.name(), username, role, refreshExpiration);

        // DB에서 deviceId에 해당하는 기존 리프레시 토큰을 삭제하고,
        // 새로운 리프레시 토큰을 저장합니다.
        refreshRepository.deleteByDeviceId(deviceId);
        tokenService.addRefreshEntity(username, newRefresh, refreshExpiration, deviceId);

        // 새로운 액세스 토큰을 DTO로 반환합니다.
        log.info("Access 토큰 및 Refresh 토큰 재발급 완료: newAccessToken = {}, newRefreshToken = {}", newAccess, newRefresh);
        return new TokenDTO(newAccess, newRefresh);
    }
}
