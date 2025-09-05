package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.user.dto.request.AdminLoginRequest;
import com.tico.pomoro_do.domain.user.dto.request.AdminRegisterRequest;
import com.tico.pomoro_do.global.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    /**
     * 관리자 회원가입
     *
     * @param adminRegisterRequest AdminRegisterRequest 객체
     * @param profileImage 프로필 이미지
     * @return 성공 시 새 Access, Refresh 토큰을 포함하는 TokenResponse
     * @throws CustomException 이메일 도메인이 유효하지 않거나 이미 등록된 사용자인 경우 예외를 던집니다.
     */
    TokenResponse registerAdmin(AdminRegisterRequest adminRegisterRequest, MultipartFile profileImage);

    /**
     * 관리자 로그인
     *
     * @param adminLoginRequest AdminLoginRequest 객체
     * @return 성공 시 새 Access, Refresh 토큰을 포함하는 TokenResponse
     * @throws CustomException 이메일 도메인이 유효하지 않거나 관리자가 아닌 경우 예외를 던집니다.
     */
    TokenResponse loginAdmin(AdminLoginRequest adminLoginRequest);
}
