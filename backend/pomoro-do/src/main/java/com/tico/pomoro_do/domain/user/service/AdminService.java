package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.user.dto.request.AdminRequest;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    // 관리자 회원가입
    TokenResponse adminJoin(AdminRequest adminRequest, MultipartFile profileImage);

    // 관리자 로그인
    TokenResponse adminLogin(AdminRequest adminRequest);
}
