package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.response.ImageResponse;
import com.tico.pomoro_do.domain.user.service.ImageService;
import com.tico.pomoro_do.global.response.SuccessCode;
import com.tico.pomoro_do.global.enums.S3Folder;
import com.tico.pomoro_do.global.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "BackEnd: 백엔드", description = "백엔드 전용 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
@Slf4j
public class ImageController {

    private final ImageService imageService;

    /**
     * 이미지 업로드 엔드포인트
     *
     * @param image MultipartRequest로 받은 파일 업로드 요청
     * @return 성공 시 ImageResponse를 포함하는 SuccessResponse 반환
     */
    @Operation(
            summary = "이미지 업로드",
            description = "사용자가 이미지를 업로드할 수 있는 엔드포인트입니다. <br>"
                    + "업로드된 이미지는 S3에 저장되며, 성공 시 이미지 URL을 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "이미지 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "유효하지 않은 이미지 파일"),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<ImageResponse>> imageUpload(
            @RequestParam("image") MultipartFile image
    ) {

        String imageUrl = imageService.imageUpload(image, S3Folder.IMAGES.getFolderName());

        ImageResponse imageResponse = ImageResponse.builder()
                .imageUrl(imageUrl)
                .build();

        SuccessResponse<ImageResponse> response = SuccessResponse.<ImageResponse>builder()
                .message(SuccessCode.IMAGE_UPLOAD_SUCCESS.getMessage())
                .data(imageResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}