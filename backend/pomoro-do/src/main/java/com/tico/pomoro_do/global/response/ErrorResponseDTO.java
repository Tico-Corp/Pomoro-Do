package com.tico.pomoro_do.global.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDTO {
    private int status;
    private String name;
    private String code;
    private String message;
//    private final String data = "";           // 응답 데이터

    @Builder
    public ErrorResponseDTO(int status, String name, String code, String message) {
        this.status = status;
        this.name = name;
        this.code = code;
        this.message = message;
    }
}