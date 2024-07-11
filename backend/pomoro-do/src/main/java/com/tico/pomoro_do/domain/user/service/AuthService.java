package com.tico.pomoro_do.domain.user.service;


import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleLoginDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    // 구글 ID 토큰 무결성 검사
    GoogleUserInfoDTO verifyGoogleIdToken(String idTokenString) throws GeneralSecurityException, IOException;

    // 구글 로그인
    JwtDTO googleLogin(GoogleLoginDTO request) throws GeneralSecurityException, IOException;

    // 구글 회원가입
    JwtDTO googleJoin(GoogleJoinDTO request) throws GeneralSecurityException, IOException;

    // 토큰 생성
    JwtDTO createJwtTokens(String email, String role);

}
