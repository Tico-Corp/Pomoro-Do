package com.tico.pomoro_do.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice // 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리한다.
public class GlobalExceptionHandler {
    //Controller 전역에서 발생하는 Custom Error를 잡아줄 Handler를 생성한다.

    // CustomException 처리
    @ExceptionHandler(CustomException.class) //발생한 CustomException 예외를 잡아서 하나의 메소드에서 공통 처리한다.
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

    // Validation 오류 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .name("Validation Error")
                .code(CustomErrorCode.VALIDATION_FAILED.getCode())
                .message(errors.toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 요청 본문이 잘못된 경우 처리
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .name("Bad Request")
                .code(CustomErrorCode.MISSING_REQUEST_BODY.getCode())
                .message("Required request body is missing.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // 요청 헤더가 누락된 경우 처리
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .name("Bad Request")
                .code(CustomErrorCode.MISSING_REQUEST_HEADER.getCode())
                .message("Required request header is missing: " + ex.getHeaderName())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ElementNotFound 예외 처리
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseEntity> handleNoSuchElementException(NoSuchElementException ex) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .name("Not Found")
                .code(CustomErrorCode.RESOURCE_NOT_FOUND.getCode())
                .message("The requested resource was not found.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // IllegalArgumentException 처리
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseEntity> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .name("Bad Request")
                .code(CustomErrorCode.INVALID_ARGUMENT.getCode())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 권한이 없는 접근 시 처리
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponseEntity> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .name("Forbidden")
                .code(CustomErrorCode.ACCESS_DENIED.getCode())
                .message("Access to the resource is denied.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    // 기타 모든 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseEntity> handleException(Exception ex) {
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .name("Internal Server Error")
                .code(CustomErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //@ControllerAdvice + @ExceptionHandler
    //모든 컨트롤러에서 발생하는 CustomException을 catch한다.
}