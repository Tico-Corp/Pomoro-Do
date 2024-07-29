package com.tico.pomoro_do.domain.user.service;


import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.enums.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    // 구글 ID 토큰 무결성 검사
    GoogleUserInfoDTO verifyGoogleIdToken(String idToken) throws GeneralSecurityException, IOException, IllegalArgumentException;

    // 구글 로그인
    TokenDTO googleLogin(String idTokenHeader, HttpServletResponse response) throws GeneralSecurityException, IOException;

    // 구글 회원가입
    TokenDTO googleJoin(String idTokenHeader, GoogleJoinDTO request, HttpServletResponse response)  throws GeneralSecurityException, IOException;

    // User 생성
    User createUser(String username, String nickname, String profileImageUrl, UserRole role);

    // 토큰 생성 및 저장
    TokenDTO generateAndStoreTokensForUser(String username, String role, HttpServletResponse response);
    TokenDTO generateAndStoreTokensForAdmin(String username, String role, String deviceId);

    // Refresh 토큰으로 Access토큰 발급
    TokenDTO reissueToken(String deviceId, String refresh);

}
