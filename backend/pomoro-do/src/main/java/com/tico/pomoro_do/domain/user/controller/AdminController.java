package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.auth.dto.response.TokenResponse;
import com.tico.pomoro_do.domain.user.dto.request.AdminLoginRequest;
import com.tico.pomoro_do.domain.user.dto.request.AdminRegisterRequest;
import com.tico.pomoro_do.domain.user.service.AdminService;
import com.tico.pomoro_do.global.response.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "BackEnd: 백엔드", description = "백엔드 전용 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
@Slf4j
public class AdminController {
    //백엔드에서는 구글 로그인이 불가하므로 생성함.
    //관리자 로그인
    //관리자 로그아웃

    private final AdminService adminService;

    /**
     * 관리자 회원가입 API
     *
     * @param request AdminRegisterRequest 객체
     * @param profileImage 관리자 프로필 이미지 파일
     * @return 성공 시 TokenResponse를 포함하는 SuccessResponse 반환
     */
    @Operation(
            summary = "관리자 회원가입",
            description = "관리자 회원가입을 수행합니다. <br>"
                    + "관리자의 이메일은 @pomorodo.invalid 도메인으로 제한됩니다. <br>"
                    + "성공 시에는 TokenResponse를 포함하는 SuccessResponse를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 등록된 사용자")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<TokenResponse>> registerAdmin(
            @Valid @RequestPart AdminRegisterRequest request,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {

        TokenResponse jwtResponse = adminService.registerAdmin(request, profileImage);
        SuccessResponse<TokenResponse> successResponse = SuccessResponse.<TokenResponse>builder()
                .message(SuccessCode.ADMIN_SIGNUP_SUCCESS.getMessage())
                .data(jwtResponse)
                .build();
        log.info("관리자 회원가입 성공: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    /**
     * 관리자 로그인 API
     *
     * @param request AdminLoginRequest 객체
     * @return 성공 시 TokenResponse를 포함하는 SuccessResponse 반환
     */
    @Operation(
            summary = "관리자 로그인",
            description = "관리자 로그인을 수행합니다. <br>"
                    + "성공 시에는 TokenResponse를 포함하는 SuccessResponse를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "관리자 권한이 없음")
    })
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<TokenResponse>> loginAdmin(
            @Valid @RequestBody AdminLoginRequest request
    ) {
        log.info("관리자 로그인 요청: {}", request.getEmail());
        TokenResponse jwtResponse = adminService.loginAdmin(request);
        SuccessResponse<TokenResponse> successResponse = SuccessResponse.<TokenResponse>builder()
                .message(SuccessCode.ADMIN_LOGIN_SUCCESS.getMessage())
                .data(jwtResponse)
                .build();
        log.info("관리자 로그인 성공: {}", request.getEmail());
        return ResponseEntity.ok(successResponse);
    }
}
