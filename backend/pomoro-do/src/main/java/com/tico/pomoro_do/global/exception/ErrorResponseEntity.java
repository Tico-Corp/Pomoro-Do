package com.tico.pomoro_do.global.exception;

import com.tico.pomoro_do.global.code.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@Schema(description = "Error Response")
public class ErrorResponseEntity {
    //Custom Error 내용을 담을 Response Entity를 생성한다.

    @Schema(description = "HTTP 상태 코드")
    private int status;
    @Schema(description = "에러 이름")
    private String name;
    @Schema(description = "커스텀 에러 코드")
    private String code;
    @Schema(description = "에러 메시지")
    private String message;
//    @Schema(description = "응답 데이터: 빈값")
//    private final String data = "";           // 응답 데이터

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
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
    //  "code": "USER-100“,
    //  "message": "사용자를 찾을 수 없습니다."
    //}

    //사용하기
    //userRepository.findById({userId})
    //	.orElseThrow(() -> new CustomException(CustomErrorCode.USER_NOT_FOUND));
}