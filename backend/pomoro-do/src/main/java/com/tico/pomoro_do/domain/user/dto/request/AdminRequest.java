package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
@Schema(description = "Admin Info")
public class AdminRequest {

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    @Schema(description = "이메일", example = "chadoll@pomorodo.shop")
    private String email;

    @Size(max = 10, message = "닉네임은 최대 10자까지 가능합니다.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(description = "닉네임", example = "chadoll")
    private String nickname;
}
