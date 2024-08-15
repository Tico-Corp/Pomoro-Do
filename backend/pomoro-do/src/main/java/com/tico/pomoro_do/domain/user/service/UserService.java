package com.tico.pomoro_do.domain.user.service;

import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;

public interface UserService {

    UserDetailDTO getMyDetail(String username);
}
