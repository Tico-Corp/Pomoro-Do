package com.tico.pomoro_do.domain.category.dto.request;

import com.tico.pomoro_do.domain.category.enums.CategoryDeletionOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "그룹 카테고리 나가기 요청")
public class CategoryLeaveRequest {

    @NotNull(message = "삭제 정책은 필수입니다.")
    @Schema(description = "탈퇴 시 할 일 삭제 정책", example = "RETAIN_ALL")
    private CategoryDeletionOption deletionOption;
}