package com.tico.pomoro_do.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtDTO {

    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtDTO(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
