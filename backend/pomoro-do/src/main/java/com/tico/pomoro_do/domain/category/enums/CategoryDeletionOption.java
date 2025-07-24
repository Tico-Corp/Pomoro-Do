package com.tico.pomoro_do.domain.category.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "삭제 정책")
public enum CategoryDeletionOption {

    @Schema(description = "모든 데이터를 유지")
    RETAIN_ALL,         // 모든 데이터 유지

    @Schema(description = "완료된 데이터만 유지")
    RETAIN_COMPLETED,  // 완료된 데이터만 유지

    @Schema(description = "모든 데이터를 삭제")
    DELETE_ALL        // 모든 데이터 삭제
}
