package com.tico.pomoro_do.global.base;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponseDTO {
    private int status;
    private String name;
    private int code;
    private String message;

    @Builder
    public ErrorResponseDTO(int status, String name, int code, String message) {
        this.status = status;
        this.name = name;
        this.code = code;
        this.message = message;
    }
}