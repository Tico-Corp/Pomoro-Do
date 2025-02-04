package com.tico.pomoro_do.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GoogleUserInfo {

    private String userId;
    private String email;
    private String name;
    private String pictureUrl;

    @Builder
    public GoogleUserInfo(String userId, String email, String name, String pictureUrl) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.pictureUrl = pictureUrl;

    }
}
