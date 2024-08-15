package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.UserDTO;
import com.tico.pomoro_do.domain.user.dto.response.UserDetailDTO;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.auth.CustomUserDetails;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
