package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.service.AuthService;
import com.tico.pomoro_do.global.base.CustomSuccessCode;
import com.tico.pomoro_do.global.base.SuccessResponseDTO;
import com.tico.pomoro_do.global.exception.CustomErrorCode;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Tag(name = "Auth: 인증", description = "인증 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    //사용자 로그인
    //사용자 로그아웃
    //액세스 토큰 및 리프레시 토큰 발급
    //토큰 갱신

    private final AuthService authService;


    /**
     * 구글 로그인 API
     *
     * @param authorizationHeader Authorization 헤더에는 구글 ID 토큰이 포함됩니다.
     * @return 성공 시 JwtDTO를 포함하는 SuccessResponseDTO
     * @throws CustomException 구글 ID 토큰 검증에 실패한 경우 예외를 던집니다.
     */
    @Operation(
            summary = "로그인",
            description = "구글 소셜 로그인을 통해 로그인을 수행합니다. <br>"
                    + "Authorization 헤더에는 구글 ID 토큰을 입력해야 합니다. <br>"
                    + "로그인 성공 시에는 JwtDTO를 포함하는 SuccessResponseDTO를 반환합니다.",
            parameters = @Parameter(
                    name = "Authorization",
                    description = "Google ID Token",
                    in = ParameterIn.HEADER,
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "구글 ID 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Authorization 헤더의 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 사용자 (code: -104)",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/google/login")
    public ResponseEntity<SuccessResponseDTO<JwtDTO>> googleLogin(@RequestHeader("Authorization") String authorizationHeader) {
        try {
            JwtDTO jwtResponse = authService.googleLogin(authorizationHeader);
            SuccessResponseDTO<JwtDTO> response = SuccessResponseDTO.<JwtDTO>builder()
                    .status(CustomSuccessCode.GOOGLE_LOGIN_SUCCESS.getHttpStatus().value())
                    .message(CustomSuccessCode.GOOGLE_LOGIN_SUCCESS.getMessage())
                    .data(jwtResponse)
                    .build();
            return ResponseEntity.ok(response);
        } catch (GeneralSecurityException | IOException e) {
            throw new CustomException(CustomErrorCode.GOOGLE_TOKEN_VERIFICATION_FAILED);
        }
    }

    /**
     * 구글 회원가입 API
     *
     * @param authorizationHeader Authorization 헤더에는 구글 ID 토큰이 포함됩니다.
     * @param requestUserInfo 회원가입 요청 정보가 포함된 DTO
     * @return 성공 시 JwtDTO를 포함하는 SuccessResponseDTO
     * @throws CustomException 구글 ID 토큰 검증에 실패한 경우 예외를 던집니다.
     */
    @Operation(
            summary = "회원 가입",
            description = "구글 소셜 로그인을 통해 회원가입을 수행합니다. <br>"
                    + "Authorization 헤더에는 구글 ID 토큰을 입력하고, 요청 본문에는 닉네임 등의 추가 정보를 포함해야 합니다. <br>"
                    + "회원가입 성공 시에는 JwtDTO를 포함하는 SuccessResponseDTO를 반환합니다.",
            parameters = @Parameter(
                    name = "Authorization",
                    description = "Google ID Token",
                    in = ParameterIn.HEADER,
                    required = true
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "401", description = "구글 ID 토큰이 유효하지 않음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "Authorization 헤더의 토큰이 유효하지 않음 또는 요청 본문이 잘못됨",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "409", description = "이미 등록된 사용자 (code: -105)",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/google/join")
    public ResponseEntity<SuccessResponseDTO<JwtDTO>> googleJoin(@RequestHeader("Authorization") String authorizationHeader,
                                                                 @RequestBody GoogleJoinDTO requestUserInfo) {
        try {
            JwtDTO jwtResponse = authService.googleJoin(authorizationHeader, requestUserInfo);
            SuccessResponseDTO<JwtDTO> response = SuccessResponseDTO.<JwtDTO>builder()
                    .status(CustomSuccessCode.GOOGLE_SIGNUP_SUCCESS.getHttpStatus().value())
                    .message(CustomSuccessCode.GOOGLE_SIGNUP_SUCCESS.getMessage())
                    .data(jwtResponse)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (GeneralSecurityException | IOException e) {
            throw new CustomException(CustomErrorCode.GOOGLE_TOKEN_VERIFICATION_FAILED);
        }
    }
}
