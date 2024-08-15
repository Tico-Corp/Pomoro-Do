package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.auth.CustomUserDetails;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User: 사용자", description = "사용자 관련 API")
@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 인증된 사용자의 상세 정보를 조회합니다.
     *
     * @param customUserDetails 인증된 사용자 정보
     * @return 성공 시 사용자 상세 정보가 담긴 SuccessResponseDTO 반환
     */
    @Operation(summary = "내 사용자 정보 조회", description = "인증된 사용자의 상세 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<SuccessResponseDTO<UserDetailDTO>> getMyDetail(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        UserDetailDTO userDetailResponse = userService.getMyDetail(customUserDetails.getUsername());
        SuccessResponseDTO<UserDetailDTO> successResponse = SuccessResponseDTO.<UserDetailDTO>builder()
                .status(SuccessCode.USER_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.USER_FETCH_SUCCESS.getMessage())
                .data(userDetailResponse)
                .build();

        return ResponseEntity.ok(successResponse);

    }

    /**
     * 주어진 사용자 ID에 대한 상세 정보를 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 성공 시 사용자 상세 정보가 담긴 SuccessResponseDTO 반환
     */
    @Operation(summary = "특정 사용자 정보 조회", description = "주어진 사용자 ID에 대한 상세 정보를 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponseDTO<UserDetailDTO>> getUserDetail(@PathVariable Long userId) {
        UserDetailDTO userDetailResponse = userService.getUserDetail(userId);
        SuccessResponseDTO<UserDetailDTO> successResponse = SuccessResponseDTO.<UserDetailDTO>builder()
                .status(SuccessCode.USER_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.USER_FETCH_SUCCESS.getMessage())
                .data(userDetailResponse)
                .build();

        return ResponseEntity.ok(successResponse);
    }

}
