package com.tico.pomoro_do.domain.category.dto.request;

import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 수정 요청")
public class CategoryUpdateRequest {

    @NotBlank(message = "카테고리 이름은 필수입니다.")
    @Schema(description = "카테고리 이름", example = "스터디")
    private String name;

    @Schema(
            description = """
            카테고리 공개 범위
            - 일반 카테고리만 수정 가능
            - 그룹 카테고리는 GROUP으로 고정 (수정 시 예외 발생)
            """,
            example = "PRIVATE"
    )
    private CategoryVisibility visibility;
}
