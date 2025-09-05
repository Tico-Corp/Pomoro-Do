package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * 관리자 회원가입 요청 DTO
 * - email: 내부 전용 도메인(@pomorodo.invalid)만 허용
 * - nickname: 최대 10자, 필수
 */
@Getter
@Schema(description = "Admin Register Request")
public class AdminRegisterRequest {

    @Schema(example = "dev-admin@pomorodo.invalid", description = "관리자 이메일")
    @NotBlank(message = "관리자 이메일을 입력해주세요.")
    @Email
    private String email;

    @Size(max = 10, message = "닉네임은 최대 10자까지 가능합니다.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Schema(example = "dev-admin", description = "닉네임")
    private String nickname;
}