package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.request.AdminDTO;
import com.tico.pomoro_do.domain.user.dto.response.TokenDTO;
import com.tico.pomoro_do.domain.user.service.AdminService;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
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

@Tag(name = "Admin: 관리자", description = "백엔드 로그인을 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
@Slf4j
public class AdminController {
    //백엔드에서는 구글 로그인이 불가하므로 생성함.
    //관리자 로그인
    //관리자 로그아웃

    private final AdminService adminService;

    /**
     * 관리자 회원가입 API
     *
     * @param adminDTO AdminDTO 객체
     * @param profileImage 관리자 프로필 이미지 파일
     * @return 성공 시 TokenDTO를 포함하는 SuccessResponseDTO
     */
    @Operation(
            summary = "관리자 회원가입",
            description = "관리자 회원가입을 수행합니다. <br>"
                    + "관리자의 이메일은 @pomorodo.shop 도메인으로 제한됩니다. <br>"
                    + "성공 시에는 TokenDTO를 포함하는 SuccessResponseDTO를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 등록된 사용자")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> adminJoin(
            @Valid @RequestPart AdminDTO adminDTO,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) {

        TokenDTO jwtResponse = adminService.adminJoin(adminDTO, profileImage);
        SuccessResponseDTO<TokenDTO> successResponse = SuccessResponseDTO.<TokenDTO>builder()
                .status(SuccessCode.ADMIN_SIGNUP_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.ADMIN_SIGNUP_SUCCESS.getMessage())
                .data(jwtResponse)
                .build();
        log.info("관리자 회원가입 성공: {}", adminDTO.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    /**
     * 관리자 로그인 API
     *
     * @param request AdminDTO 객체
     * @return 성공 시 TokenDTO를 포함하는 SuccessResponseDTO
     */
    @Operation(
            summary = "관리자 로그인",
            description = "관리자 로그인을 수행합니다. <br>"
                    + "성공 시에는 TokenDTO를 포함하는 SuccessResponseDTO를 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "등록되지 않은 사용자"),
            @ApiResponse(responseCode = "403", description = "관리자 권한이 없음")
    })
    @PostMapping("/login")
    public ResponseEntity<SuccessResponseDTO<TokenDTO>> adminLogin(
            @Valid @RequestBody AdminDTO request
    ) {
        log.info("관리자 로그인 요청: {}", request.getUsername());
        TokenDTO jwtResponse = adminService.adminLogin(request);
        SuccessResponseDTO<TokenDTO> successResponse = SuccessResponseDTO.<TokenDTO>builder()
                .status(SuccessCode.ADMIN_LOGIN_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.ADMIN_LOGIN_SUCCESS.getMessage())
                .data(jwtResponse)
                .build();
        log.info("관리자 로그인 성공: {}", request.getUsername());
        return ResponseEntity.ok(successResponse);
    }
}
