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

    // 토큰 형태 검사
    String extractToken(String header, TokenType tokenType);

    // 구글 로그인
    JwtDTO googleLogin(String idTokenHeader) throws GeneralSecurityException, IOException;

    // 구글 회원가입
    JwtDTO googleJoin(String idTokenHeader, GoogleJoinDTO request)  throws GeneralSecurityException, IOException;

    // User 생성
    User createUser(String username, String nickname, String profileImageUrl, UserRole role);

    // 토큰 생성
    JwtDTO createJwtTokens(String email, String role);

    // Refresh 토큰으로 Access토큰 발급
    TokenDTO reissueToken(HttpServletRequest request, HttpServletResponse response);

}
