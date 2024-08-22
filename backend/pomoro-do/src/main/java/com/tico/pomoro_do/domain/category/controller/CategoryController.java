package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryDetailDTO;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDTO;
import com.tico.pomoro_do.domain.category.dto.response.GroupInviteDTO;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.global.auth.CustomUserDetails;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category: 카테고리", description = "투두리스트의 카테고리 관련 API")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 생성 API
     *
     * @param customUserDetails 현재 인증된 사용자의 세부 정보
     * @param request 카테고리 생성 요청 DTO (제목, 유형, 공개 설정, 색상, 그룹 멤버 등 포함)
     * @return 카테고리 생성 성공 메시지 및 상태 코드
     *
     */
    @Operation(
            summary = "카테고리 생성",
            description = "현재 인증된 사용자가 카테고리를 생성합니다. <br>" +
                    "카테고리는 일반 카테고리 또는 그룹 카테고리일 수 있습니다. 카테고리 유형은 `GENERAL` (일반) 또는 `GROUP` (그룹)입니다. <br>" +
                    "그룹 카테고리를 생성하는 경우, 요청 본문에 그룹 멤버를 지정할 수 있습니다. <br>" +
                    "카테고리의 공개 설정은 `PUBLIC` (전체 공개), `FOLLOWERS` (팔로워만 공개), `PRIVATE` (비공개), `GROUP` (그룹 공개) 중 하나로 설정할 수 있습니다. <br>" +
                    "성공적으로 카테고리가 생성되면 HTTP 상태 코드 201(Created)과 함께 성공 메시지가 반환됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리가 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 요청 데이터가 유효하지 않거나 필수 필드가 누락됨"),
            @ApiResponse(responseCode = "401", description = "인증 실패 - 인증된 사용자가 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 - 서버에서 예기치 않은 오류 발생")
    })
    @PostMapping
    public ResponseEntity<SuccessResponseDTO<String>> createCategory(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid @RequestBody CategoryDetailDTO request
    ){
        String username = customUserDetails.getUsername();
        categoryService.createCategory(username, request);

        SuccessResponseDTO<String> successResponse = SuccessResponseDTO.<String>builder()
                .status(SuccessCode.CATEGORY_CREATION_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_CREATION_SUCCESS.getMessage())
                .data(SuccessCode.CATEGORY_CREATION_SUCCESS.name())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);

    }

    @Operation(
            summary = "일반 및 그룹 카테고리 조회",
            description = "현재 인증된 사용자의 그룹 및 일반 카테고리를 조회합니다. <br>" +
                    "일반 카테고리와 그룹 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
    )
    @GetMapping
    public ResponseEntity<SuccessResponseDTO<CategoryDTO>> getCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String username = customUserDetails.getUsername();
        CategoryDTO categoryDTO = categoryService.getCategories(username);

        SuccessResponseDTO<CategoryDTO> successResponse = SuccessResponseDTO.<CategoryDTO>builder()
                .status(SuccessCode.CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_FETCH_SUCCESS.getMessage())
                .data(categoryDTO)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "초대된 모든 그룹 카테고리의 초대장을 조회",
            description = "현재 인증된 사용자가 초대된 모든 그룹 카테고리 초대장을 조회합니다. <br>" +
                    "초대된 그룹이 없을 경우 빈 배열([])을 반환하며, " +
                    "정상적인 응답일 경우 초대장 목록이 포함된 배열을 반환합니다."
    )
    @GetMapping("/group/invitations")
    public ResponseEntity<SuccessResponseDTO<List<GroupInviteDTO>>> getInvitedGroupCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String username = customUserDetails.getUsername();
        List<GroupInviteDTO> groupCategories = categoryService.getInvitedGroupCategories(username);

        SuccessResponseDTO<List<GroupInviteDTO>> successResponse = SuccessResponseDTO.<List<GroupInviteDTO>>builder()
                .status(SuccessCode.CATEGORY_INVITED_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_INVITED_FETCH_SUCCESS.getMessage())
                .data(groupCategories)
                .build();
        return ResponseEntity.ok(successResponse);
    }

}
