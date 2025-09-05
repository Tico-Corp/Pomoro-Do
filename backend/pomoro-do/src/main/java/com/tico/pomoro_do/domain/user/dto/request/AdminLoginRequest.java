package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "Admin Login Request")
public class AdminLoginRequest {

    @Schema(example = "dev-admin@pomorodo.invalid", description = "관리자 이메일")
    @NotBlank
    @Email
    private String email;

    @Schema(example = "dev-admin", description = "닉네임")
    @NotBlank
    private String nickname;
}