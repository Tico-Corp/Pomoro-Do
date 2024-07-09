package com.tico.pomoro_do.global.base;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SuccessResponseDTO<T> {
    private final int status;       // 응답 코드 200번대
    private final String message;   // 메시지
    private final T data;           // 응답 데이터

    @Builder
    public SuccessResponseDTO(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
