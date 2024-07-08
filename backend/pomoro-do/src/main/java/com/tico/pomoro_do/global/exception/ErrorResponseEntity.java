package com.tico.pomoro_do.global.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {
    //Custom Error 내용을 담을 Response Entity를 생성한다.
    private int status;
    private String name;
    private int code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(CustomErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .name(e.name())
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build()
                );
    }

    //Response 결과 예시
    //{
    //  "status": 404,
    //  "name": "USER_NOT_FOUND",
    //  "code": -1000,
    //  "message": "사용자를 찾을 수 없습니다."
    //}

    //사용하기
    //userRepository.findById({userId})
    //	.orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
}