package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.request.AdminJoinDTO;
import com.tico.pomoro_do.domain.user.dto.request.AdminLoginDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;

public interface AdminService {

    //관리자 회원가입
    JwtDTO adminJoin(AdminJoinDTO adminJoinDTO);

    //관리자 로그인
    JwtDTO adminLogin(AdminLoginDTO adminLoginDTO);
}
