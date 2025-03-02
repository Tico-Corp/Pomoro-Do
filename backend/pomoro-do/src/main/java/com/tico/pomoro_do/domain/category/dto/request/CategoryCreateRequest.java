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
public class CategoryCreateRequest {

    @NotNull(message = "날짜를 입력해주세요.")
    @Schema(description = "카테고리 생성일", example = "2014-07-24")
    private LocalDate startDate;

    @NotBlank(message = "이름을 입력해주세요.")
    @Schema(description = "카테고리 이름", example = "New Category")
    private String categoryName;

    @NotNull(message = "카테고리 유형을 입력해주세요.")
    @Schema(description = "카테고리 유형", example = "PERSONAL")
    private CategoryType categoryType;

    @NotNull(message = "카테고리 공개 설정을 입력해주세요.")
    @Schema(description = "카테고리 공개 설정", example = "PUBLIC")
    private CategoryVisibility categoryVisibility;

    // 그룹 카테고리일 경우 그룹원 ID 리스트를 Set으로 받아 중복 제거
    @Schema(description = "그룹 카테고리일 경우, 그룹 멤버들의 ID 리스트", example = "[1, 2, 3]")
    private Set<Long> groupMemberIds;

}
