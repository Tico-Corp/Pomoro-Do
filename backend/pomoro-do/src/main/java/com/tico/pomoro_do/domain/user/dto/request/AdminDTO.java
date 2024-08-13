package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "Admin Info")
public class AdminDTO {

    @Email
    @NotBlank(message = "이메일을 입력해주세요.")
    private String username;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
}
