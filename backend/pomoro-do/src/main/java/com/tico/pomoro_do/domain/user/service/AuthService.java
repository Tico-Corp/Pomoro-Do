package com.tico.pomoro_do.domain.user.service;


import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.UserRole;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    // 구글 ID 토큰 무결성 검사
    GoogleUserInfoDTO verifyGoogleIdToken(String idToken) throws GeneralSecurityException, IOException, IllegalArgumentException;

    // 구글 로그인
    TokenDTO googleLogin(String idTokenHeader, String deviceId) throws GeneralSecurityException, IOException;

    // 구글 회원가입
    TokenDTO googleJoin(String idTokenHeader, String deviceId, String nickname, MultipartFile profileImage)  throws GeneralSecurityException, IOException;

    // User 생성
    User createUser(String username, String nickname, String profileImageUrl, UserRole role);

    // 토큰 생성 및 저장
    TokenDTO generateAndStoreTokens(String username, String role, String deviceId);

    // Refresh 토큰으로 Access토큰 발급
    TokenDTO reissueToken(String deviceId, String refresh);

}
