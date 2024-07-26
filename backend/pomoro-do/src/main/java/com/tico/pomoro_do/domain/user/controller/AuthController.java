package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.service.AuthService;
import com.tico.pomoro_do.domain.user.service.TokenService;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Tag(name = "Auth: 인증", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {
    // 사용자 로그인
    // 사용자 로그아웃
    // 액세스 토큰 및 리프레시 토큰 발급
    // 토큰 갱신

    private final AuthService authService;
    private final TokenService tokenService;
    //jwt관리 및 검증 utill
    private final JWTUtil jwtUtil;

    /**
     * 구글 로그인 API
     *
     * @param googleIdTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param response HttpServletResponse 객체
     * @return 성공 시 JwtDTO를 포함하는 SuccessResponseDTO
     * @throws CustomException 구글 ID 토큰 검증에 실패한 경우 예외를 던집니다.
     */
    @Operation(
            summary = "구글 로그인",
            description = "구글 소셜 로그인을 통해 사용자를 인증하고 JWT 토큰을 발급합니다. <br>"
                    + "Google-ID-Token 헤더에 구글 ID 토큰을 입력해야 합니다. 예시: Bearer <id_token>",
            parameters = @Parameter(
                    name = "Google-ID-Token",
                    description = "Google ID Token",
                    in = ParameterIn.HEADER,
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "구글 ID 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Google-ID-Token 헤더의 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 사용자 (code: U-104)",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/google/login")
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> googleLogin(
            @RequestHeader("Google-ID-Token") String googleIdTokenHeader,
            HttpServletResponse response
    ) {
        try {
            TokenDTO jwtResponse = authService.googleLogin(googleIdTokenHeader, response);
            SuccessResponseDTO<TokenDTO> successResponse = SuccessResponseDTO.<TokenDTO>builder()
                    .status(SuccessCode.GOOGLE_LOGIN_SUCCESS.getHttpStatus().value())
                    .message(SuccessCode.GOOGLE_LOGIN_SUCCESS.getMessage())
                    .data(jwtResponse)
                    .build();
            return ResponseEntity.ok(successResponse);
        } catch (GeneralSecurityException | IOException | IllegalArgumentException e) {
            log.error("구글 ID 토큰 검증 실패: {}", e.getMessage(), e);
            throw new CustomException(ErrorCode.GOOGLE_TOKEN_VERIFICATION_FAILED);
        }
    }

    /**
     * 구글 회원가입 API
     *
     * @param googleIdTokenHeader Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param requestUserInfo 회원가입 요청 정보가 포함된 DTO
     * @param response HttpServletResponse 객체
     * @return 성공 시 JwtDTO를 포함하는 SuccessResponseDTO
     * @throws CustomException 구글 ID 토큰 검증에 실패한 경우 예외를 던집니다.
     */
    @Operation(
            summary = "구글 회원가입",
            description = "구글 소셜 로그인을 통해 사용자를 회원가입하고 JWT 토큰을 발급합니다. <br>"
                    + "Google-ID-Token 헤더에 구글 ID 토큰을 입력하고, 요청 본문에는 추가 정보를 포함해야 합니다.",
            parameters = @Parameter(
                    name = "Google-ID-Token",
                    description = "Google ID Token",
                    in = ParameterIn.HEADER,
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "401", description = "구글 ID 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Google-ID-Token 헤더의 토큰이 유효하지 않음 또는 요청 본문이 잘못됨",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "409", description = "이미 등록된 사용자 (code: U-105)",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/google/join")
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> googleJoin(
            @RequestHeader("Google-ID-Token") String googleIdTokenHeader,
            @Valid @RequestBody GoogleJoinDTO requestUserInfo,
            HttpServletResponse response
    ) {
        try {
            TokenDTO jwtResponse = authService.googleJoin(googleIdTokenHeader, requestUserInfo, response);
            SuccessResponseDTO<TokenDTO> successResponse = SuccessResponseDTO.<TokenDTO>builder()
                    .status(SuccessCode.GOOGLE_SIGNUP_SUCCESS.getHttpStatus().value())
                    .message(SuccessCode.GOOGLE_SIGNUP_SUCCESS.getMessage())
                    .data(jwtResponse)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
        } catch (GeneralSecurityException | IOException | IllegalArgumentException e) {
            log.error("구글 ID 토큰 검증 실패: {}", e.getMessage(), e);
            throw new CustomException(ErrorCode.GOOGLE_TOKEN_VERIFICATION_FAILED);
        }
    }

    /**
     * 토큰 재발급 API
     *
     * @param deviceId 기기 고유 번호
     * @param refreshToken 리프레시 토큰
     * @return 재발급된 토큰을 포함하는 SuccessResponseDTO
     */
    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰 및 리프레시 토큰을 재발급합니다.",
            parameters = {
                    @Parameter(
                            name = "Device-Id",
                            description = "기기 고유 번호",
                            in = ParameterIn.HEADER,
                            required = true
                    ),
                    @Parameter(
                            name = "Refresh-Token",
                            description = "리프레시 토큰",
                            in = ParameterIn.HEADER,
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "401", description = "리프레시 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "기기 ID 또는 리프레시 토큰이 DB에 존재하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/token/reissue")
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> reissueToken(
            @RequestHeader("Device-Id") String deviceId,
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        // AuthService의 reissueToken 메서드 호출하여 결과 받기
        TokenDTO tokenDTO = authService.reissueToken(deviceId, refreshToken);

        SuccessResponseDTO<TokenDTO> successResponse = SuccessResponseDTO.<TokenDTO>builder()
                .status(SuccessCode.ACCESS_TOKEN_REISSUED.getHttpStatus().value())
                .message(SuccessCode.ACCESS_TOKEN_REISSUED.getMessage())
                .data(tokenDTO)
                .build();

        // 재발행 성공 시, HTTP 상태 코드 200(OK)와 함께 결과 반환
        return ResponseEntity.ok(successResponse);
    }

//    /**
//     * 액세스 토큰 검증 API
//     * 이 엔드포인트는 JWT 인증이 필요하지 않습니다.
//     *
//     * @param tokenHeader X-Auth-Token 헤더로 전달된 액세스 토큰
//     * @return 토큰 검증 결과를 반환합니다.
//     */
//    @Operation(
//            summary = "액세스 토큰 검증",
//            description = "전달된 액세스 토큰이 유효한지 검증합니다."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "토큰 검증 성공"),
//            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않음",
//                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
//            @ApiResponse(responseCode = "400", description = "토큰 헤더가 없음",
//                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
//    })
//    @GetMapping("/token/validate")
//    public ResponseEntity<SuccessResponseDTO<String>> validateAccessToken(
//            @RequestHeader("X-Auth-Token") String tokenHeader
//    ) {
//        String token = authService.extractToken(tokenHeader, TokenType.JWT);
//        tokenService.validateToken(token, "access");
//        SuccessResponseDTO<String> successResponse = SuccessResponseDTO.<String>builder()
//                .status(SuccessCode.ACCESS_TOKEN_VALIDATED.getHttpStatus().value())
//                .message(SuccessCode.ACCESS_TOKEN_VALIDATED.getMessage())
//                .data(SuccessCode.ACCESS_TOKEN_VALIDATED.name()) // data가 없을 때는 null로 설정
//                .build();
//
//        // 검증 성공 시, HTTP 상태 코드 200(OK)와 함께 결과 반환
//        return ResponseEntity.ok(successResponse);
//    }

    /**
     * 로그아웃 API
     * 로그아웃하여 Refresh 토큰을 삭제합니다.
     *
     * @param request  HTTP 요청 객체
     * @param response HTTP 응답 객체
     * @return 로그아웃 및 토큰 삭제 결과를 반환합니다.
     */
    @Operation(
            summary = "로그아웃 및 토큰 만료",
            description = "사용자가 로그아웃할 때, 쿠키의 Refresh 토큰을 만료시켜 삭제합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = @Content(schema = @Schema(implementation = SuccessResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "로그아웃 요청이 잘못됨",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @DeleteMapping("/logout")
    public ResponseEntity<SuccessResponseDTO<String>> removeToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 액세스 토큰으로 현재 Redis 정보 삭제
        tokenService.removeRefreshToken(request, response);

        SuccessResponseDTO<String> successResponse = SuccessResponseDTO.<String>builder()
                .status(SuccessCode.LOGOUT_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.LOGOUT_SUCCESS.getMessage())
                .data(SuccessCode.LOGOUT_SUCCESS.name()) // data가 없을 때는 null로 설정
                .build();

        // 토큰 삭제 성공 응답 처리
        return ResponseEntity.ok(successResponse);
    }

}