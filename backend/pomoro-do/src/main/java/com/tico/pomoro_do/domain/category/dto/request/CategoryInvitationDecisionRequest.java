package com.tico.pomoro_do.domain.category.dto.request;

import com.tico.pomoro_do.domain.category.enums.CategoryInvitationDecision;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 초대 응답 요청")
public class CategoryInvitationDecisionRequest {

    @NotNull(message = "초대 응답은 필수입니다.")
    @Schema(description = "카테고리 초대 응답 유형 (ACCEPTED/REJECTED)", example = "ACCEPTED")
    private CategoryInvitationDecision decision;
}