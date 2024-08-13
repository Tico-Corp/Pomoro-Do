package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.request.AdminDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    // 관리자 회원가입
    TokenDTO adminJoin(AdminDTO adminDTO, MultipartFile profileImage);

    // 관리자 로그인
    TokenDTO adminLogin(AdminDTO adminDTO);
}
