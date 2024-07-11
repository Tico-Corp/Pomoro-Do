package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated  // 유효성 검사를 위해 추가
@Schema(description = "Google Join Info")
public class GoogleJoinDTO {

    @Schema(description = "유저의 닉네임",  nullable = false, example = "chadoll")
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
