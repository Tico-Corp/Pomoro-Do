package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.request.AdminJoinDTO;
import com.tico.pomoro_do.domain.user.dto.request.AdminLoginDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    // 관리자 회원가입
    TokenDTO adminJoin(AdminJoinDTO adminJoinDTO, MultipartFile profileImage);

    // 관리자 로그인
    TokenDTO adminLogin(AdminLoginDTO adminLoginDTO);
}
