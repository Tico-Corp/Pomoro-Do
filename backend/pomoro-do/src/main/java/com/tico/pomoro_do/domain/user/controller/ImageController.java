package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.response.ImageDTO;
import com.tico.pomoro_do.domain.user.service.ImageService;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "Image: 이미지", description = "이미지 업로드 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    /**
     * 이미지 업로드 엔드포인트
     *
     * @param file MultipartRequest로 받은 파일 업로드 요청
     * @return ResponseEntity 성공 응답 DTO를 포함한 ResponseEntity 객체
     */
    @PostMapping("/upload")
    public ResponseEntity<SuccessResponseDTO<ImageDTO>> imageUpload(@RequestParam("file") MultipartFile file) {

        String imageUrl = imageService.imageUpload(file, "images");

        ImageDTO imageDTO = ImageDTO.builder()
                .url(imageUrl)
                .build();

        SuccessResponseDTO<ImageDTO> response = SuccessResponseDTO.<ImageDTO>builder()
                .status(SuccessCode.IMAGE_UPLOAD_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.IMAGE_UPLOAD_SUCCESS.getMessage())
                .data(imageDTO)
                .build();

        return ResponseEntity.ok(response);
    }
}