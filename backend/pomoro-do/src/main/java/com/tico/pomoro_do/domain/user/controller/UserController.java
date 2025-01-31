package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.response.FollowResponse;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.domain.auth.security.CustomUserDetails;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User: 사용자", description = "사용자 관련 API")
@RestController
@RequestMapping("api/v1/users")
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
    @Operation(summary = "현재 사용자 정보 조회", description = "인증된 사용자의 상세 정보를 조회합니다.")
    @GetMapping("/me")
    public ResponseEntity<SuccessResponseDTO<UserDetailDTO>> getMyDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {

        UserDetailDTO userDetailResponse = userService.getMyDetail(customUserDetails.getUsername());
        SuccessResponseDTO<UserDetailDTO> successResponse = SuccessResponseDTO.<UserDetailDTO>builder()
                .status(SuccessCode.USER_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.USER_FETCH_SUCCESS.getMessage())
                .data(userDetailResponse)
                .build();

        return ResponseEntity.ok(successResponse);

    }

    /**
     * 특정 사용자의 상세 정보 및 팔로우 상태를 조회합니다.
     *
     * @param userId 조회할 사용자 ID
     * @return 성공 시 사용자 정보와 팔로우 상태가 담긴 SuccessResponseDTO 반환
     */
    @Operation(summary = "특정 사용자 정보 조회", description = "주어진 사용자 ID에 대한 상세 정보 및 팔로우 상태를 조회합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<SuccessResponseDTO<FollowResponse>> getUserDetail(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId
    ) {
        String username = customUserDetails.getUsername();
        FollowResponse response = userService.getUserDetail(username, userId);
        SuccessResponseDTO<FollowResponse> successResponse = SuccessResponseDTO.<FollowResponse>builder()
                .status(SuccessCode.USER_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.USER_FETCH_SUCCESS.getMessage())
                .data(response)
                .build();

        return ResponseEntity.ok(successResponse);
    }

    /**
     * 현재 인증된 사용자의 계정을 삭제합니다.
     *
     * @param customUserDetails 현재 인증된 사용자 정보를 담고 있는 CustomUserDetails 객체
     * @param deviceId 사용자 디바이스의 고유 식별자
     * @param refreshToken 현재 사용자 디바이스의 리프레시 토큰
     * @return 삭제 성공 시 SuccessResponseDTO를 포함하는 ResponseEntity 반환
     */
    @Operation(summary = "현재 사용자 계정 삭제", description = "현재 인증된 사용자의 계정을 삭제합니다.")
    @DeleteMapping("/me")
    public ResponseEntity<SuccessResponseDTO<String>> deleteUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestHeader("Device-ID") String deviceId,
            @RequestHeader("Refresh-Token") String refreshToken
    ) {
        userService.deleteUser(customUserDetails.getUsername(), deviceId, refreshToken);

        SuccessResponseDTO<String> successResponse = SuccessResponseDTO.<String>builder()
                .status(SuccessCode.USER_DELETION_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.USER_DELETION_SUCCESS.getMessage())
                .data(SuccessCode.USER_DELETION_SUCCESS.name())
                .build();
        return ResponseEntity.ok(successResponse);
    }

}
