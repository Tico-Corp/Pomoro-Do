package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.request.AdminJoinDTO;
import com.tico.pomoro_do.domain.user.dto.request.AdminLoginDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.service.AdminService;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import com.tico.pomoro_do.global.exception.ErrorResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "admin: 관리자", description = "백엔드를 테스트를 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Slf4j
public class AdminController {
    //백엔드에서는 구글 로그인이 불가하므로 생성함.
    //관리자 로그인
    //관리자 로그아웃

    private final AdminService adminService;

    /**
     * 관리자 회원가입 API
     *
     * @param request AdminJoinDTO 객체
     * @return 성공 시 JwtDTO를 포함하는 SuccessResponseDTO
     */
    @Operation(
            summary = "관리자 회원가입",
            description = "관리자 회원가입을 수행합니다. <br>"
                    + "관리자의 이메일은 @pomorodo.shop 도메인으로 제한됩니다. <br>"
                    + "성공 시에는 JwtDTO를 포함하는 SuccessResponseDTO를 반환합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "AdminJoinDTO 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AdminJoinDTO.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "409", description = "이미 등록된 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/join")
    public ResponseEntity<SuccessResponseDTO<JwtDTO>> adminJoin(@RequestBody AdminJoinDTO request) {
        log.info("관리자 회원가입 요청: {}", request.getUsername());
        JwtDTO jwtResponse = adminService.adminJoin(request);
        SuccessResponseDTO<JwtDTO> response = SuccessResponseDTO.<JwtDTO>builder()
                .status(SuccessCode.ADMIN_SIGNUP_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.ADMIN_SIGNUP_SUCCESS.getMessage())
                .data(jwtResponse)
                .build();
        log.info("관리자 회원가입 성공: {}", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * 관리자 로그인 API
     *
     * @param request AdminLoginDTO 객체
     * @return 성공 시 JwtDTO를 포함하는 SuccessResponseDTO
     */
    @Operation(
            summary = "관리자 로그인",
            description = "관리자 로그인을 수행합니다. <br>"
                    + "성공 시에는 JwtDTO를 포함하는 SuccessResponseDTO를 반환합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "AdminLoginDTO 객체",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AdminLoginDTO.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class))),
            @ApiResponse(responseCode = "403", description = "관리자 권한이 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponseEntity.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDTO<JwtDTO>> adminLogin(@RequestBody AdminLoginDTO request) {
        log.info("관리자 로그인 요청: {}", request.getUsername());
        JwtDTO jwtResponse = adminService.adminLogin(request);
        SuccessResponseDTO<JwtDTO> response = SuccessResponseDTO.<JwtDTO>builder()
                .status(SuccessCode.ADMIN_LOGIN_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.ADMIN_LOGIN_SUCCESS.getMessage())
                .data(jwtResponse)
                .build();
        log.info("관리자 로그인 성공: {}", request.getUsername());
        return ResponseEntity.ok(response);
    }
}
