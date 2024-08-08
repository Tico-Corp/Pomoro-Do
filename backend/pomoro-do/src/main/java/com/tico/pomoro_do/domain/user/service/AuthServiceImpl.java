package com.tico.pomoro_do.domain.user.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.entity.Refresh;
import com.tico.pomoro_do.domain.user.entity.SocialLogin;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.RefreshRepository;
import com.tico.pomoro_do.domain.user.repository.SocialLoginRepository;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.enums.SocialProvider;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.enums.UserRole;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

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
    @Transactional
    public TokenDTO generateAndStoreTokens(String username, String role, String deviceId) {
        // DB에서 deviceId에 해당하는 기존 리프레시 토큰을 삭제
        refreshRepository.deleteByDeviceId(deviceId);

        // 액세스 토큰 생성
        String accessToken = jwtUtil.createJwt("access", username, role, accessExpiration); // 60분
        // 리프레시 토큰 생성
        String refreshToken = jwtUtil.createJwt("refresh", username, role, refreshExpiration); // 24시간
        // 리프레시 토큰을 DB에 저장
        tokenService.addRefreshEntity(username, refreshToken, refreshExpiration, deviceId);

        return new TokenDTO(accessToken, refreshToken);
    }

    /**
     * 사용자 인증을 위한 액세스 토큰과 리프레시 토큰을 생성하고 저장
     *
     * @param username 사용자 이메일
     * @param role 사용자 역할
     * @param response HttpServletResponse 객체, 생성된 리프레시 토큰을 쿠키로 추가하기 위해 사용됨
     * @return TokenDTO 객체, 생성된 액세스 토큰을 포함
     */
    @Override
    @Transactional
    public TokenDTO generateAndStoreTokensForUser(String username, String role, HttpServletResponse response) {
        log.info("Access 토큰 및 Refresh 토큰 생성: 이메일 = {}, 역할 = {}", username, role);

        // 액세스 토큰 생성
        String accessToken = jwtUtil.createJwt("access", username, role, accessExpiration); // 60분
        // 리프레시 토큰 생성
        String refreshToken = jwtUtil.createJwt("refresh", username, role, refreshExpiration); // 24시간
        // 리프레시 토큰을 DB에 저장
//        tokenService.addRefreshEntity(username, refreshToken, refreshExpiration);

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
        // 토큰 추출
        String idToken = jwtUtil.extractToken(idTokenHeader, TokenType.GOOGLE);
        // 구글 토큰 유효성 검증
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(idToken);
        // 회원 가입 여부 판단
        if (!userRepository.existsByUsername(userInfo.getEmail())) {
            log.error("사용자 등록되지 않음: 이메일 = {}", userInfo.getEmail());
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return generateAndStoreTokens(userInfo.getEmail(), String.valueOf(UserRole.USER), deviceId);
    }

    /**
     * 구글 ID 토큰으로 회원가입 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param requestUserInfo GoogleJoinDTO 객체
     * @param response HttpServletResponse 객체
     * @return TokenDTO를 포함하는 객체
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 이미 등록된 사용자인 경우 예외
     */
    @Override
    @Transactional
    public TokenDTO googleJoin(String idTokenHeader, GoogleJoinDTO requestUserInfo, HttpServletResponse response) throws GeneralSecurityException, IOException {
        log.info("구글 회원가입 처리 시작");

        String idToken = jwtUtil.extractToken(idTokenHeader, TokenType.GOOGLE);
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(idToken);

        if (userRepository.existsByUsername(userInfo.getEmail())) {
            log.error("이미 등록된 사용자: 이메일 = {}", userInfo.getEmail());
            throw new CustomException(ErrorCode.USER_ALREADY_REGISTERED);
        }

        // 사용자 정보 저장
        User user = createUser(
                userInfo.getEmail(),
                requestUserInfo.getNickname(),
                userInfo.getPictureUrl(),
                UserRole.USER
        );

        // 소셜 로그인 정보 저장
        SocialLogin socialLogin = SocialLogin.builder()
                .user(user)
                .provider(SocialProvider.GOOGLE)
                .socialId(userInfo.getUserId())
                .build();
        socialLoginRepository.save(socialLogin);

        log.info("구글 회원가입 성공: 이메일 = {}", userInfo.getEmail());
        return generateAndStoreTokensForUser(userInfo.getEmail(), String.valueOf(UserRole.USER), response);
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
    @Transactional
    public User createUser(String username, String nickname, String profileImageUrl, UserRole role) {
        log.info("새 사용자 생성: 이메일 = {}, 닉네임 = {}", username, nickname);

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
     * Refresh 토큰을 사용하여 Access 토큰 재발급
     *
     * @param deviceId 기기 고유 번호
     * @param refreshHeader 리프레시 토큰
     * @return 새 Access 토큰을 포함하는 TokenDTO
     */
    @Transactional
    @Override
    public TokenDTO reissueToken(String deviceId, String refreshHeader) {
        log.info("Refresh 토큰으로 Access 토큰 재발급 시도: deviceId = {}", deviceId);
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

        log.info("새로운 Access, Refresh 토큰 생성");

        // 새로운 액세스 및 리프레시 토큰을 생성합니다.
        String newAccess = jwtUtil.createJwt("access", username, role, accessExpiration); // 60분
        String newRefresh = jwtUtil.createJwt("refresh", username, role, refreshExpiration);

        // DB에서 deviceId에 해당하는 기존 리프레시 토큰을 삭제하고,
        // 새로운 리프레시 토큰을 저장합니다.
        refreshRepository.deleteByDeviceId(deviceId);
        tokenService.addRefreshEntity(username, newRefresh, refreshExpiration, deviceId);

        // 새로운 액세스 토큰을 DTO로 반환합니다.
        log.info("Access 토큰 및 Refresh 토큰 재발급 완료: newAccessToken = {}, newRefreshToken = {}", newAccess, newRefresh);
        return new TokenDTO(newAccess, newRefresh);
    }
}
