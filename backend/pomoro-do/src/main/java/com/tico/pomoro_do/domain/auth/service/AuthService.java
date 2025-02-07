package com.tico.pomoro_do.domain.auth.service;


import com.tico.pomoro_do.domain.auth.dto.GoogleUserInfo;
import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.global.enums.ProfileImageType;
import com.tico.pomoro_do.global.enums.UserRole;
import com.tico.pomoro_do.global.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface AuthService {

    /**
     * 구글 ID 토큰으로 무결성 검증
     *
     * @param idToken 구글 ID 토큰
     * @return 검증된 GoogleUserInfo
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않은 경우 예외
     */
    GoogleUserInfo verifyGoogleIdToken(String idToken) throws GeneralSecurityException, IOException, IllegalArgumentException;

    /**
     * 구글 ID 토큰으로 로그인 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @return TokenResponse를 포함하는 객체
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 등록되지 않은 사용자인 경우 예외
     */
    TokenResponse googleLogin(String idTokenHeader, String deviceId) throws GeneralSecurityException, IOException;

    /**
     * 구글 ID 토큰으로 회원가입 처리
     *
     * @param idTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @param nickname 닉네임
     * @param profileImage 프로필 이미지
     * @param imageType 프로필 이미지 타입
     * @return TokenResponse를 포함하는 객체
     * @throws GeneralSecurityException 구글 ID 토큰 검증 중 발생하는 보안 예외
     * @throws IOException IO 예외
     * @throws CustomException 구글 ID 토큰이 유효하지 않거나 이미 등록된 사용자인 경우 예외
     */
    TokenResponse googleJoin(String idTokenHeader, String deviceId, String nickname, MultipartFile profileImage, ProfileImageType imageType)  throws GeneralSecurityException, IOException;

    /**
     * 새 사용자 생성
     *
     * @param email 사용자 이메일
     * @param nickname 사용자 닉네임
     * @param profileImageUrl 사용자 프로필 이미지 URL
     * @param role 사용자 역할
     * @return 생성된 User 객체
     */
    User createUser(String email, String nickname, String profileImageUrl, UserRole role);

    /**
     * Refresh 토큰을 사용하여 Access 토큰 재발급
     *
     * @param deviceId 기기 고유 번호
     * @param refreshHeader 리프레시 토큰
     * @return 새 Access 토큰을 포함하는 TokenResponse
     */
    TokenResponse reissueToken(String deviceId, String refreshHeader);

}
