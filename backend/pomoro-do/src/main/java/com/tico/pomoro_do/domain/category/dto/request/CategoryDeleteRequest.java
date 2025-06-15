package com.tico.pomoro_do.domain.category.dto.request;

import com.tico.pomoro_do.domain.category.enums.CategoryDeletionOption;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 삭제 요청")
public class CategoryDeleteRequest {

    @NotNull(message = "삭제 정책은 필수입니다.")
    @Schema(description = "삭제 정책", example = "DELETE_ALL")
    private CategoryDeletionOption deletionOption;
}