package com.tico.pomoro_do.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "Success Response")
public class SuccessResponse<T> {

    @Schema(description = "성공 코드", nullable = false, example = "SUCCESS")
    private final String code = "SUCCESS";
    @Schema(description = "상태 메세지", nullable = false, example = "요청이 성공적으로 처리되었습니다.")
    private final String message;   // 메시지
    @Schema(description = "응답 데이터", nullable = true)
    private final T data;           // 응답 데이터

    @Builder
    public SuccessResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // 정적 팩토리 메서드 추가
    public static <T> SuccessResponse<T> of(SuccessCode successCode, T data) {
        return SuccessResponse.<T>builder()
                .message(successCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> SuccessResponse<T> of(SuccessCode successCode) {
        return SuccessResponse.<T>builder()
                .message(successCode.getMessage())
                .message(successCode.name())
                .build();
    }
}
