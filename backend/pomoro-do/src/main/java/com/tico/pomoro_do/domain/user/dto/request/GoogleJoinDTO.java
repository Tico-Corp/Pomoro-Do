package com.tico.pomoro_do.domain.user.dto.request;

import lombok.Getter;

@Getter
public class GoogleJoinDTO {

    private String idToken;
    private String nickname;
}
