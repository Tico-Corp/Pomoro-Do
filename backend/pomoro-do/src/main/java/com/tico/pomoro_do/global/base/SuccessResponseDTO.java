package com.tico.pomoro_do.global.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "Success Response")
public class SuccessResponseDTO<T> {

    @Schema(description = "상태 코드", nullable = false, example = "200")
    private final int status;       // 응답 코드 200번대
    @Schema(description = "상태 메세지", nullable = false, example = "성공하였습니다.")
    private final String message;   // 메시지
    @Schema(description = "해당 API의 응답 데이터", nullable = false)
    private final T data;           // 응답 데이터

    @Builder
    public SuccessResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
