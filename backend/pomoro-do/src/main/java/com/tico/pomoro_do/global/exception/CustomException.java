package com.tico.pomoro_do.global.exception;


import com.tico.pomoro_do.global.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러를 사용하기 위한 구현체
 *
 *  @author BUGYEONG
 */
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException{
    //ErrorCode를 담을 class 생성한다.
    //RuntimeException 상속
    //Custom Error Code 속성 추가

    ErrorCode errorCode;
}