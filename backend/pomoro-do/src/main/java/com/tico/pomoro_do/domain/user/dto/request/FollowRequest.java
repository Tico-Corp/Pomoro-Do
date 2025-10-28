package com.tico.pomoro_do.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "팔로우 요청")
public class FollowRequest {

    @NotNull(message = "대상 사용자 ID는 필수입니다")
    private Long targetUserId;
}