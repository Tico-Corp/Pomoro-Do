package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.GoogleUserInfoDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleJoinDTO;
import com.tico.pomoro_do.domain.user.dto.request.GoogleLoginDTO;
import com.tico.pomoro_do.domain.user.dto.response.JwtDTO;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.AuthService;
import com.tico.pomoro_do.global.base.CustomSuccessCode;
import com.tico.pomoro_do.global.base.SuccessResponseDTO;
import com.tico.pomoro_do.global.exception.CustomErrorCode;
import com.tico.pomoro_do.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/google/login")
    public ResponseEntity<SuccessResponseDTO<JwtDTO>> googleLogin(@RequestBody GoogleLoginDTO request) {
        try {
            JwtDTO jwtResponse = authService.googleLogin(request);
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

    @PostMapping("/google/join")
    public ResponseEntity<SuccessResponseDTO<JwtDTO>> googleJoin(@RequestBody GoogleJoinDTO request) {
        try {
            JwtDTO jwtResponse = authService.googleJoin(request);
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
