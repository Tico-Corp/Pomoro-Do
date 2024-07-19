package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;


@Getter
@Schema(description = "Google Join Info")
public class GoogleJoinDTO {

    @Schema(description = "유저의 닉네임",  nullable = false, example = "chadoll")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
