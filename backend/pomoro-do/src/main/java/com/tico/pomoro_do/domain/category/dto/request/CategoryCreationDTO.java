package com.tico.pomoro_do.domain.category.dto.request;

import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.enums.CategoryVisibility;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
public class CategoryCreationDTO {

    @NotNull(message = "날짜를 입력해주세요.")
    @Schema(description = "카테고리 생성 날짜", example = "2014-07-24")
    private LocalDate date;

    @NotBlank(message = "제목을 입력해주세요.")
    @Schema(description = "카테고리의 제목", example = "My New Category")
    private String title;

    @NotNull(message = "카테고리 유형을 입력해주세요.")
    @Schema(description = "카테고리의 유형", example = "GENERAL")
    private CategoryType type;

    @NotNull(message = "카테고리 공개 설정을 입력해주세요.")
    @Schema(description = "카테고리의 공개 설정", example = "PUBLIC")
    private CategoryVisibility visibility;

    @NotBlank(message = "카테고리 색상을 입력해주세요.")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "유효한 색상 코드를 입력해주세요. 예: #FF5733")
    @Schema(description = "카테고리의 색상", example = "#FF5733")
    private String color;

    // 그룹 카테고리일 경우 그룹원 ID 리스트를 Set으로 받아 중복 제거
    // 리스트가 Set<Long> 타입으로 자동 변환됨
    @Schema(description = "그룹 카테고리일 경우, 그룹 멤버들의 ID 리스트", example = "[1, 2, 3]")
    private Set<Long> members;

}
