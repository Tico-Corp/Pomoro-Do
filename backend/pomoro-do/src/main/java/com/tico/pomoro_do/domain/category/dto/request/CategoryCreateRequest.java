package com.tico.pomoro_do.domain.category.dto.request;

import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Schema(description = "카테고리 생성 요청")
public class CategoryCreateRequest {

    @NotNull(message = "시작일은 필수입니다")
    @Schema(description = "카테고리 시작일", example = "2014-07-24")
    private LocalDate startDate;

    @NotBlank(message = "카테고리 이름은 필수입니다")
    @Schema(description = "카테고리 이름", example = "New Category")
    private String name;

    @NotNull(message = "카테고리 유형은 필수입니다")
    @Schema(description = "카테고리 유형 (PERSONAL/GROUP)", example = "PERSONAL")
    private CategoryType type;

    @NotNull(message = "공개 설정은 필수입니다")
    @Schema(description = "카테고리 공개 설정 (PUBLIC/FOLLOWERS/PRIVATE/GROUP)", example = "PUBLIC")
    private CategoryVisibility visibility;

    // 그룹 카테고리일 경우 그룹원 ID 리스트를 Set으로 받아 중복 제거
    @Schema(description = "그룹 카테고리일 경우 초대할 멤버 ID 목록", example = "[1, 2, 3]")
    private Set<Long> memberIds;
}
