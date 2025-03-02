package com.tico.pomoro_do.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonParseException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 전역 예외 처리 핸들러 클래스.
 * 모든 컨트롤러에서 발생할 수 있는 예외를 잡아 처리하며, 적절한 HTTP 응답을 클라이언트에게 반환한다.
 *
 * @author BUGYEONG
 */
@Slf4j
@ControllerAdvice // 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리한다.
public class GlobalExceptionHandler {
    //Controller 전역에서 발생하는 Custom Error를 잡아줄 Handler를 생성한다.


    /**
     * 사용자 정의 예외(CustomException) 처리.
     *
     * @param e CustomException
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(CustomException.class) //발생한 CustomException 예외를 잡아서 하나의 메소드에서 공통 처리한다.
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException e) {
        log.error("CustomException: ", e);
        return ErrorResponseEntity.toResponseEntity(e.getErrorCode());
    }

    /**
     * 요청 객체의 데이터 유효성 검사 실패(MethodArgumentNotValidException) 처리.
     * API 호출 시 파라미터 데이터가 유효하지 않은 경우 발생한다.
     *
     * @param ex MethodArgumentNotValidException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("MethodArgumentNotValidException: ", ex);
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.VALIDATION_FAILED.getCode())
                .message("Validation failed for fields: " + errors.toString())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청 헤더가 누락된 경우(MissingRequestHeaderException) 처리.
     *
     * @param ex MissingRequestHeaderException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {
        log.error("MissingRequestHeaderException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.MISSING_REQUEST_HEADER.getCode())
                .message("Required request header is missing: " + ex.getHeaderName())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청 본문이 잘못된 경우(HttpMessageNotReadableException) 처리.
     * 클라이언트에서 요청 본문이 유효하지 않은 경우 발생한다.
     *
     * @param ex HttpMessageNotReadableException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.MISSING_REQUEST_BODY.getCode())
                .message("Required request body is missing or unreadable.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청 파라미터가 누락된 경우(MissingServletRequestParameterException) 처리.
     *
     * @param ex MissingServletRequestParameterException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("MissingServletRequestParameterException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.MISSING_REQUEST_PARAMETER.getCode())
                .message("Required request parameter is missing: " + ex.getParameterName())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 클라이언트 요청이 잘못된 경우(HttpClientErrorException.BadRequest) 처리.
     *
     * @param e HttpClientErrorException.BadRequest 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleBadRequestException(HttpClientErrorException e) {
        log.error("HttpClientErrorException.BadRequest: ", e);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.BAD_REQUEST.getCode())
                .message("Required request is missing. Bad request: " + e.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 요청 파라미터의 타입 불일치(MethodArgumentTypeMismatchException) 처리.
     * API 호출 시 파라미터의 타입이 예상과 다른 경우 발생한다.
     *
     * @param ex MethodArgumentTypeMismatchException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("MethodArgumentTypeMismatchException: ", ex);

        String parameterName = ex.getName();
        String parameterType = Objects.requireNonNull(ex.getRequiredType()).getSimpleName();
        String message = String.format("Parameter '%s' should be of type '%s'.", parameterName, parameterType);

        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.INVALID_PARAMETER_TYPE.getCode())
                .message(message)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 잘못된 요청 처리(IllegalArgumentException).
     *
     * @param ex IllegalArgumentException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("IllegalArgumentException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.INVALID_TYPE_VALUE.getCode())
                .message("Invalid argument: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 입력/출력 처리 중 오류 발생(IOException) 처리.
     *
     * @param ex IOException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleIOException(IOException ex) {
        log.error("IOException occurred: {}", ex.getMessage(), ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.IO_ERROR.getCode())
                .message("I/O error occurred: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * JSON 파싱 오류(JsonParseException) 처리.
     *
     * @param ex JsonParseException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(JsonParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleJsonParseException(JsonParseException ex) {
        log.error("JsonParseException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.JSON_PARSE_ERROR.getCode())
                .message("Failed to parse JSON: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * JSON 처리 오류(JsonProcessingException) 처리.
     *
     * @param ex JsonProcessingException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleJsonProcessingException(JsonProcessingException ex) {
        log.error("JsonProcessingException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.JSON_PROCESSING_ERROR.getCode())
                .message("Failed to process JSON: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * JSON 매핑 오류(JsonMappingException) 처리.
     *
     * @param ex JsonMappingException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(JsonMappingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleJsonMappingException(JsonMappingException ex) {
        log.error("JsonMappingException occurred: {}", ex.getMessage(), ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.JSON_MAPPING_ERROR.getCode())
                .message("Failed to map JSON: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 멀티파트 데이터 처리 중 오류 발생(MultipartException) 처리.
     *
     * @param ex MultipartException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleMultipartException(MultipartException ex) {
        log.error("MultipartException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.INVALID_MULTIPART_DATA.getCode())
                .message("Failed to process multipart request: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 업로드 최대 용량을 초과했을 경우(MaxUploadSizeExceededException) 처리.
     *
     * @param ex MaxUploadSizeExceededException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        log.error("MaxUploadSizeExceededException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED.getCode())
                .message("The uploaded file exceeds the allowed maximum size. Please upload a file within the allowed size.")                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 쿠키 값이 없을 때(MissingRequestCookieException) 처리.
     * 요청에서 쿠키가 누락된 경우 발생한다.
     *
     * @param ex MissingRequestCookieException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MissingRequestCookieException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ErrorResponseEntity> handleMissingRequestCookieException(MissingRequestCookieException ex) {
        log.error("MissingRequestCookieException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCode.INVALID_COOKIE.getCode())
                .message("Required cookie is missing.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 권한 없는 요청 처리(HttpClientErrorException.Forbidden).
     *
     * @param ex HttpClientErrorException.Forbidden 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ErrorResponseEntity> handleForbiddenException(HttpClientErrorException.Forbidden ex) {
        log.error("HttpClientErrorException.Forbidden: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .code(ErrorCode.FORBIDDEN.getCode())
                .message("Forbidden: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * 권한 없는 접근 시 발생하는 예외 처리(AccessDeniedException).
     *
     * @param ex AccessDeniedException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    protected ResponseEntity<ErrorResponseEntity> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("AccessDeniedException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.FORBIDDEN.value())
                .code(ErrorCode.ACCESS_DENIED.getCode())
                .message("Access Denied: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * 존재하지 않는 엔티티 접근 시 발생하는 예외 처리(EntityNotFoundException).
     *
     * @param ex EntityNotFoundException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseEntity> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("EntityNotFoundException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ErrorCode.ENTITY_NOT_FOUND.getCode())
                .message("Entity Not Found: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 존재하지 않는 핸들러 접근 시 발생하는 예외 처리(NoHandlerFoundException).
     * 클라이언트가 존재하지 않는 경로로 요청할 때 발생한다.
     *
     * @param ex NoHandlerFoundException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseEntity> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("NoHandlerFoundException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ErrorCode.NO_HANDLER_FOUND.getCode())
                .message("No handler found for the requested URL [" + ex.getRequestURL() + "]. Please ensure the URL is correct.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 사용자 정의 리소스가 없는 경우(NoResourceFoundException) 처리.
     * 예를들어, 데이터베이스에서 사용자나 기타 리소스를 찾으려 할 때 해당 리소스가 없으면 이 예외가 발생
     *
     * @param ex NoResourceFoundException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseEntity> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("NoResourceFoundException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ErrorCode.NO_RESOURCE_FOUND.getCode())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 요청한 리소스를 찾을 수 없는 경우(NoSuchElementException) 처리.
     * 예를 들어, Optional에서 get() 메서드를 호출할 때 값이 없으면 이 예외가 발생
     *
     * @param ex NoSuchElementException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<ErrorResponseEntity> handleNoSuchElementException(NoSuchElementException ex) {
        log.error("NoSuchElementException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code(ErrorCode.NOT_FOUND.getCode())
                .message("The requested resource was not found.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * 인증이 필요한 요청에서 발생하는 예외(AuthenticationException) 처리.
     * 인증이 필요하지만 인증 정보가 제공되지 않았거나 잘못된 경우 발생합니다.
     *
     * @param ex AuthenticationException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ErrorResponseEntity> handleAuthenticationException(AuthenticationException ex) {
        log.error("AuthenticationException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .code(ErrorCode.UNAUTHORIZED.getCode())
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * JWT 서명 오류 처리(SignatureException).
     *
     * @param ex SignatureException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(SignatureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ErrorResponseEntity> handleSignatureException(SignatureException ex) {
        log.error("SignatureException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .code(ErrorCode.INVALID_JWT_SIGNATURE.getCode())
                .message("JWT signature verification failed.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 잘못된 JWT 처리(MalformedJwtException).
     *
     * @param ex MalformedJwtException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    protected ResponseEntity<ErrorResponseEntity> handleMalformedJwtException(MalformedJwtException ex) {
        log.error("MalformedJwtException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .code(ErrorCode.INVALID_MALFORMED_JWT.getCode())
                .message("Malformed JWT token.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 지원하지 않는 HTTP 메서드 호출 시 발생하는 예외 처리(HttpRequestMethodNotSupportedException).
     *
     * @param ex HttpRequestMethodNotSupportedException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected ResponseEntity<ErrorResponseEntity> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code(ErrorCode.METHOD_NOT_ALLOWED.getCode())
                .message("Method Not Allowed: " + ex.getMethod())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * NULL 값이 발생한 경우(NullPointerException) 처리.
     * 서버 내부에서 예상치 못한 null 값이 발생했을 때 발생
     *
     * @param ex NullPointerException 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponseEntity> handleNullPointerException(NullPointerException ex) {
        log.error("NullPointerException: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCode.NULL_POINTER.getCode())
                .message("A null pointer exception occurred.")
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 서버 내부 오류 발생(Exception) 처리.
     * 기타 예외 처리.
     *
     * @param ex Exception 발생 예외
     * @return ResponseEntity<ErrorResponseEntity> 에러 응답
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ErrorResponseEntity> handleException(Exception ex) {
        log.error("Exception: ", ex);
        ErrorResponseEntity errorResponse = ErrorResponseEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                .message("An unexpected error occurred: " + ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //@ControllerAdvice + @ExceptionHandler
    //모든 컨트롤러에서 발생하는 CustomException을 catch한다.
}