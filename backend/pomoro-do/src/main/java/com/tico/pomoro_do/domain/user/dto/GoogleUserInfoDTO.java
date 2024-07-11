package com.tico.pomoro_do.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleUserInfoDTO {

    private String userId;
    private String email;
    private String name;
    private String pictureUrl;

    @Builder
    public GoogleUserInfoDTO(String userId, String email, String name, String pictureUrl) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;

    }
}
