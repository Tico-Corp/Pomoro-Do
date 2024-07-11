package com.tico.pomoro_do.domain.user.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleLoginDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.entity.SocialLogin;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.repository.SocialLoginRepository;
import com.tico.pomoro_do.domain.user.repository.UserRepository;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.common.enums.SocialProvider;
import com.tico.pomoro_do.global.common.enums.UserRole;
import com.tico.pomoro_do.global.common.enums.UserStatus;
import com.tico.pomoro_do.global.exception.CustomErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
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

    @Override
    public GoogleUserInfoDTO verifyGoogleIdToken(String idToken) throws GeneralSecurityException, IOException {
        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        if (googleIdToken != null) {
            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return GoogleUserInfoDTO.builder()
                    .userId(payload.getSubject())
                    .email(payload.getEmail())
                    .name((String) payload.get("name"))
                    .pictureUrl((String) payload.get("picture"))
                    .build();
        } else {
            throw new CustomException(CustomErrorCode.GOOGLE_TOKEN_INVALID);
        }
    }

    @Override
    public JwtDTO createJwtTokens(String email, String role) {
        //토큰 생성 (카테고리, 유저이름, 역할, 만료시간)
        String accessToken = jwtUtil.createJwt("access", email, role, accessExpiration); //10분
        String refreshToken = jwtUtil.createJwt("refresh", email, role, refreshExpiration); //24시간
        return new JwtDTO(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public JwtDTO googleLogin(GoogleLoginDTO request) throws GeneralSecurityException, IOException {
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(request.getIdToken());

        User user = userRepository.findByUsername(userInfo.getEmail())
                .orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_REGISTERED));

        return createJwtTokens(userInfo.getEmail(), String.valueOf(UserRole.USER));
    }

    @Override
    @Transactional
    public JwtDTO googleJoin(GoogleJoinDTO request) throws GeneralSecurityException, IOException {
        GoogleUserInfoDTO userInfo = verifyGoogleIdToken(request.getIdToken());

        if (userRepository.findByUsername(userInfo.getEmail()).isPresent()) {
            throw new CustomException(CustomErrorCode.USER_ALREADY_REGISTERED);
        }

        // 사용자 정보 저장
        User user = User.builder()
                .username(userInfo.getEmail())
                .nickname(request.getNickname())
                .profileImageUrl(userInfo.getPictureUrl())
                .role(UserRole.USER)
                .status(UserStatus.ACTIVE)
                .build();
        userRepository.save(user);

        // 소셜 로그인 정보 저장
        SocialLogin socialLogin = SocialLogin.builder()
                .user(user)
                .provider(SocialProvider.GOOGLE)
                .socialId(userInfo.getUserId())
                .build();
        socialLoginRepository.save(socialLogin);

        return createJwtTokens(userInfo.getEmail(), String.valueOf(UserRole.USER));
    }

}
