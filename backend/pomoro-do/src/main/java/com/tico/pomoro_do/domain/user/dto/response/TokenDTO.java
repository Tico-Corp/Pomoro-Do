package com.tico.pomoro_do.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "JWT Response")
public class TokenDTO {

    @Schema(description = "Access Token", nullable = false)
    private String accessToken;

    @Builder
    public TokenDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
