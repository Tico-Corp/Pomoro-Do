package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.service.AuthService;
import com.tico.pomoro_do.domain.user.service.TokenService;
import com.tico.pomoro_do.global.auth.jwt.JWTUtil;
import com.tico.pomoro_do.global.code.ErrorCode;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.enums.ProfileImageType;
import com.tico.pomoro_do.global.enums.TokenType;
import com.tico.pomoro_do.global.exception.CustomException;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * @param googleIdToken Google-ID-Token 헤더에 포함된 구글 ID 토큰
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @return 성공 시 JWT 토큰 정보가 담긴 SuccessResponseDTO 반환
     * @throws CustomException 구글 ID 토큰 검증 실패 시 CustomException 발생
     */
    @Operation(
            summary = "구글 로그인",
            description = "구글 소셜 로그인을 통해 사용자를 인증하고 JWT 토큰을 발급합니다. <br>"
                    + "Google-ID-Token 헤더에 구글 ID 토큰을 입력해야 합니다. 예시: Bearer <id_token> <br>"
                    + "Device-ID 헤더에 기기의 고유 번호를 입력해야 합니다.",
            parameters = {
                    @Parameter(
                            name = "Google-ID-Token",
                            description = "Google ID Token",
                            in = ParameterIn.HEADER,
                            required = true
                    ),
                    @Parameter(
                            name = "Device-ID",
                            description = "기기 고유 번호",
                            in = ParameterIn.HEADER,
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "Google-ID-Token 헤더 또는 Device-ID 헤더의 값이 유효하지 않음"),
            @ApiResponse(responseCode = "401", description = "구글 ID 토큰이 유효하지 않음"),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 사용자 (code: U-104)")
    })
    @PostMapping("/google/login")
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> googleLogin(
            @RequestHeader("Google-ID-Token") String googleIdToken,
            @RequestHeader("Device-ID") String deviceId
    ) {
        try {
            // AuthService를 통해 구글 로그인을 처리하고 JWT 토큰을 발급받습니다.
            TokenDTO jwtResponse = authService.googleLogin(googleIdToken, deviceId);

            // 성공 응답 생성
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
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @param nickname 닉네임
     * @param profileImage 프로필 이미지
     * @return 성공 시 TokenDTO를 포함하는 SuccessResponseDTO
     * @throws CustomException 구글 ID 토큰 검증에 실패한 경우 예외를 던집니다.
     */
    @Operation(
            summary = "구글 회원가입",
            description = "구글 소셜 로그인을 통해 사용자를 회원가입하고 JWT 토큰을 발급합니다. <br>"
                    + "Google-ID-Token 헤더에 구글 ID 토큰을 입력하고, 요청 본문에는 추가 정보를 포함해야 합니다. <br>"
                    + "Device-ID 헤더에 기기의 고유 번호를 입력해야 합니다. <br>"
                    + "프로필 이미지의 유형에 따라 프로필 이미지 처리 방법이 달라집니다. <br>"
                    + "- `FILE`: 사용자가 업로드한 프로필 이미지 <br>"
                    + "- `GOOGLE`: 구글 프로필 이미지 <br>"
                    + "- `DEFAULT`: 서비스 기본 프로필 이미지",
            parameters = {
                    @Parameter(
                            name = "Google-ID-Token",
                            description = "Google ID Token",
                            in = ParameterIn.HEADER,
                            required = true
                    ),
                    @Parameter(
                            name = "Device-ID",
                            description = "기기 고유 번호",
                            in = ParameterIn.HEADER,
                            required = true
                    ),
                    @Parameter(
                            name = "nickname",
                            description = "사용자 닉네임",
                            required = true
                    ),
                    @Parameter(
                            name = "profileImageType",
                            description = "프로필 이미지 유형 (FILE, GOOGLE, DEFAULT)",
                            required = true
                    )
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "401", description = "구글 ID 토큰이 유효하지 않음"),
            @ApiResponse(responseCode = "400", description = "헤더의 토큰이 유효하지 않음 또는 요청 본문이 잘못됨"),
            @ApiResponse(responseCode = "409", description = "이미 등록된 사용자 (code: U-105)")
    })
    @PostMapping(value = "/google/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> googleJoin(
            @RequestHeader("Google-ID-Token") String googleIdTokenHeader,
            @RequestHeader("Device-ID") String deviceId,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImageType") ProfileImageType imageType,
            @RequestParam(value = "profileImage", required = false) MultipartFile profileImage
    ) {
        try {
            TokenDTO jwtResponse = authService.googleJoin(googleIdTokenHeader, deviceId, nickname, profileImage, imageType);
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
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @param refreshToken Refresh-Token 헤더에 포함된 리프레시 토큰
     * @return 재발급된 JWT 토큰 정보가 담긴 SuccessResponseDTO 반환
     * @throws CustomException 리프레시 토큰 검증 실패 시 CustomException 발생
     */
    @Operation(
            summary = "토큰 재발급",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰 및 리프레시 토큰을 재발급합니다.",
            parameters = {
                    @Parameter(
                            name = "Device-ID",
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
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "401", description = "리프레시 토큰이 유효하지 않음"),
            @ApiResponse(responseCode = "404", description = "기기 ID 또는 리프레시 토큰이 DB에 존재하지 않음"),
    })
    @PostMapping("/token/reissue")
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> reissueToken(
            @RequestHeader("Device-ID") String deviceId,
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

    /**
     * 로그아웃 API
     *
     * 사용자가 로그아웃 시, 서버의 리프레시 토큰을 삭제합니다.
     *
     * @param deviceId Device-ID 헤더에 포함된 기기 고유 번호
     * @param refreshToken Refresh-Token 헤더에 포함된 리프레시 토큰
     * @return 로그아웃 성공 메시지를 담은 SuccessResponseDTO 반환
     * @throws CustomException 리프레시 토큰 삭제 실패 시 CustomException 발생
     */
    @Operation(
            summary = "로그아웃 및 토큰 삭제",
            description = "사용자가 로그아웃할 때, 서버의 Refresh 토큰을 삭제합니다.",
            parameters = {
                    @Parameter(
                            name = "Device-ID",
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
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @DeleteMapping("/logout")
    public ResponseEntity<SuccessResponseDTO<String>> removeToken(
            @RequestHeader("Device-ID") String deviceId,
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        // 액세스 토큰으로 현재 Redis 정보 삭제
        tokenService.removeRefreshToken(deviceId, refreshToken);

        SuccessResponseDTO<String> successResponse = SuccessResponseDTO.<String>builder()
                .status(SuccessCode.LOGOUT_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.LOGOUT_SUCCESS.getMessage())
                .data(SuccessCode.LOGOUT_SUCCESS.name()) // data가 없을 때는 null로 설정
                .build();

        // 토큰 삭제 성공 응답 처리
        return ResponseEntity.ok(successResponse);
    }

    /**
     * JWT 토큰 검증 API
     *
     * 전달된 JWT 토큰이 유효한지 검증합니다.
     * 이 엔드포인트는 JWT 인증이 필요하지 않습니다.
     *
     * @param request HTTP 요청 객체
     * @param tokenType 검증할 토큰 타입 (ACCESS 또는 REFRESH)
     * @return 토큰 검증 성공 메시지를 담은 SuccessResponseDTO 반환
     * @throws CustomException 토큰 검증 실패 시 CustomException 발생
     */
    @Operation(
            summary = "JWT 토큰 검증",
            description = "전달된 JWT 토큰이 유효한지 검증합니다. Authorization 헤더에 JWT 토큰을 입력합니다. " +
                    "토큰 타입은 'tokenType' 파라미터로 지정할 수 있습니다. ('ACCESS' 또는 'REFRESH')"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 검증 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 토큰 헤더 없음"),
            @ApiResponse(responseCode = "401", description = "토큰이 유효하지 않음")
    })
    @GetMapping("/token/validate")
    public ResponseEntity<SuccessResponseDTO<String>> validateToken(HttpServletRequest request, @RequestParam("tokenType") TokenType tokenType) {

        String tokenHeader = request.getHeader("Authorization");
        // 헤더에서 토큰 추출
        // Authorization 헤더가 없거나 형식이 올바르지 않은 경우: Authorization 헤더 오류 반환
        String token = jwtUtil.extractToken(tokenHeader, TokenType.ACCESS);
        // 토큰 검증
        jwtUtil.validateToken(token, tokenType);
        // SuccessCode 반환
        SuccessCode successCode = tokenService.getSuccessCodeForTokenType(tokenType);;
        SuccessResponseDTO<String> successResponse = SuccessResponseDTO.<String>builder()
                .status(successCode.getHttpStatus().value())
                .message(successCode.getMessage())
                .data(successCode.name()) // data가 없을 때는 null로 설정
                .build();

        // 검증 성공 시, HTTP 상태 코드 200(OK)와 함께 결과 반환
        return ResponseEntity.ok(successResponse);
    }

}