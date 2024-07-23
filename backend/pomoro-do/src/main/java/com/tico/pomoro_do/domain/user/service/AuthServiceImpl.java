package com.tico.pomoro_do.domain.user.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.entity.SocialLogin;
import com.tico.pomoro_do.domain.user.entity.User;
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
            throw new CustomException(ErrorCode.GOOGLE_TOKEN_VERIFICATION_FAILED);
        }
    }

    /**
     * Access 토큰, Refresh 토큰 발급
     *
     * @param email 사용자 이메일
     * @param role 사용자 역할
     * @return JwtDTO 객체
     */
    @Override
    public JwtDTO createJwtTokens(String email, String role) {
        //토큰 생성 (카테고리, 유저이름, 역할, 만료시간)
        String accessToken = jwtUtil.createJwt("access", email, role, accessExpiration); //10분
        String refreshToken = jwtUtil.createJwt("refresh", email, role, refreshExpiration); //24시간
        return new JwtDTO(accessToken, refreshToken);
    }

    /**
     * 토큰 관련 헤더에서 토큰 값을 추출합니다.
     *
     * @param header 토큰 헤더 (예: "Bearer <token>")
     * @param tokenType 토큰의 타입 (Google ID 토큰 또는 JWT)
     * @return 추출된 토큰 값
     * @throws CustomException 토큰 헤더가 유효하지 않은 경우 예외 발생
     *                         - 헤더가 null이거나 비어있는 경우
     *                         - 헤더 형식이 "Bearer <token>" 형식이 아닌 경우
     *                         - 토큰 타입이 Google ID 토큰인데 헤더 형식이 맞지 않는 경우
     */
    @Override
    public String extractToken(String header, TokenType tokenType) {

        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            ErrorCode errorCode = tokenType.equals(TokenType.GOOGLE)
                    ? ErrorCode.INVALID_GOOGLE_TOKEN_HEADER
                    : ErrorCode.INVALID_AUTHORIZATION_HEADER;
            throw new CustomException(errorCode);
        }

        return header.substring(7);
    }

    /**
     * 구글 ID 토큰으로 로그인 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @return JwtDTO를 포함하는 ResponseEntity
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 사용자가 등록되어 있지 않은 경우 예외
     */
    @Override
    @Transactional
    public JwtDTO googleLogin(String idTokenHeader) throws GeneralSecurityException, IOException {

        String idToken = extractToken(idTokenHeader, TokenType.GOOGLE);
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(idToken);

        if (!userRepository.existsByUsername(userInfo.getEmail())) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        return createJwtTokens(userInfo.getEmail(), String.valueOf(UserRole.USER));
    }

    /**
     * 구글 ID 토큰으로 회원가입 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param requestUserInfo GoogleJoinDTO 객체
     * @return JwtDTO를 포함하는 ResponseEntity
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 이미 등록된 사용자인 경우 예외
     */
    @Override
    @Transactional
    public JwtDTO googleJoin(String idTokenHeader, GoogleJoinDTO requestUserInfo) throws GeneralSecurityException, IOException {
        String idToken = extractToken(idTokenHeader, TokenType.GOOGLE);
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(idToken);

        if (userRepository.existsByUsername(userInfo.getEmail())) {
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

        return createJwtTokens(userInfo.getEmail(), String.valueOf(UserRole.USER));
    }

    /**
     * USER 생성하기
     *
     * @param username 사용자 이메일
     * @param nickname 사용자 닉네임
     * @param profileImageUrl 사용자 프로필 이미지 URL
     * @param role 사용자 역할
     * @return 생성된 User 객체
     */
    @Override
    @Transactional
    public User createUser(String username, String nickname, String profileImageUrl, UserRole role){

        // 사용자 정보 저장
        User user = User.builder()
                .username(username)
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .role(role)
                .build();
        userRepository.save(user);

        return user;
    }

    /** Refresh 토큰으로 Access 토큰을 재발급 **/
    @Transactional
    @Override
    public TokenDTO reissueToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("Refresh 토큰으로 Access 토큰 재발급");

        String refresh = CookieUtil.getRefreshToken(request);

        log.info("Refresh 토큰 검증 시작");
        // 토큰 검증 로직
        if (refresh == null) {
            log.info("Refresh 토큰이 없습니다.");
            //response status code
            throw new CustomException(ErrorCode.MISSING_REFRESH_TOKEN);
//            return new ResponseEntity<>("refresh token null", HttpStatus.BAD_REQUEST);
        }

        log.info("토큰이 존재합니다.");
        //소멸 시간 검증
        //expired check
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            log.info("Refresh 토큰이 만료되었습니다.");
            //response status code
            throw new CustomException(ErrorCode.REFRESH_TOKEN_EXPIRED);
//            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
        }

        // 리프레쉬 토큰 만료 안됐으면 리프레쉬 토큰이 맞는 지 확인
        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            log.info("유효하지 않은 Refresh 토큰입니다. Refresh 토큰이 아닙니다.");
            //response status code
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
//            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
        }
        //토큰 검증 완료
        log.info("Refresh 토큰 검증 완료");

        // 토큰에서 사용자 정보 획득
        // Extract username and role from refresh token
        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        log.info("새로운 Access, Refresh 토큰 생성");
        //make new JWT
        //토큰 생성 (카테고리, 유저이름, 역할, 만료시간)
        String newAccess = jwtUtil.createJwt("access", username, role, accessExpiration); //60분

        //response
        //응답 설정: header
        //access 토큰 헤더에 넣어서 응답 (key: value 형태) -> 예시) access: 인증토큰(string)
//        response.setHeader("access", newAccess);

        return new TokenDTO(newAccess);
    }
}
