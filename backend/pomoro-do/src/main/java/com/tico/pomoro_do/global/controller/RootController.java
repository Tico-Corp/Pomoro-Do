package com.tico.pomoro_do.global.controller;

import com.tico.pomoro_do.global.response.AppInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BackEnd: 백엔드", description = "백엔드 전용 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/info")
public class RootController {

    @Operation(summary = "애플리케이션 기본 정보", description = "애플리케이션의 기본 정보를 제공합니다.")
    @GetMapping
    public ResponseEntity<AppInfoDTO> getInfo() {
        AppInfoDTO response = new AppInfoDTO(
                "Pomoro-Do!",
                "1.0.0",
                "Pomoro-Do! 서비스에 오신 것을 환영합니다.",
                "서비스가 정상적으로 실행 중입니다.",
                "/swagger-ui.html"
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}