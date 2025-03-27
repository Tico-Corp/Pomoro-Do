package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.domain.user.service.UserService;
import com.tico.pomoro_do.global.response.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponse;
import com.tico.pomoro_do.global.security.auth.CustomUserDetails;
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
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;
    private final UserService userService;

    /**
     * 카테고리 생성 API
     *
     * @param userDetails 현재 인증된 사용자의 세부 정보
     * @param request 카테고리 생성 요청 DTO
     * @return 생성된 카테고리 ID
     *
     */
    @Operation(
            summary = "카테고리 생성",
            description = "사용자의 개인 또는 그룹 카테고리를 생성합니다.\n\n"
                    + "**카테고리 유형**\n"
                    + "- `PERSONAL`: 개인 카테고리\n"
                    + "- `GROUP`: 그룹 카테고리 (멤버 초대 가능)\n\n"
                    + "**공개 설정**\n"
                    + "- `PUBLIC`: 전체 공개\n"
                    + "- `FOLLOWERS`: 팔로워에게만 공개\n"
                    + "- `PRIVATE`: 비공개\n"
                    + "- `GROUP`: 그룹 멤버에게만 공개\n\n"
                    + "그룹 카테고리를 생성하면 요청자는 자동으로 `OWNER` 역할을 부여받으며, 지정한 멤버들에게 초대장이 발송됩니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 요청 데이터가 유효하지 않거나 필수 필드가 누락됨"),
            @ApiResponse(responseCode = "401", description = "인증 실패 - 인증된 사용자가 없음"),
            @ApiResponse(responseCode = "500", description = "서버 오류 - 서버에서 예기치 않은 오류 발생")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<Long>> createCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CategoryCreateRequest request
    ){
        Long categoryId = categoryService.processCategoryCreation(userDetails.getUserId(), request);

        SuccessResponse<Long> successResponse = SuccessResponse.<Long>builder()
                .status(SuccessCode.CATEGORY_CREATION_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_CREATION_SUCCESS.getMessage())
                .data(categoryId)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    /**
     * 카테고리 목록 조회 API
     */
    @Operation(
            summary = "카테고리 목록 조회",
            description = "사용자의 카테고리를 전체 또는 유형별로 조회합니다.\n\n"
                    + "**조회 유형 (type 파라미터)**\n"
                    + "- 파라미터 없음: 모든(개인, 그룹, 초대) 카테고리 (기본값)\n"
                    + "- `PERSONAL`: 개인 카테고리\n"
                    + "- `GROUP`: 그룹 카테고리\n\n"
                    + "**조회 대상**\n"
                    + "- 개인 카테고리: 이름순 정렬\n"
                    + "- 그룹 카테고리: 이름순 정렬\n"
                    + "- 초대받은 카테고리: 응답 대기 중인 그룹 카테고리 초대장 최신순 정렬\n\n"
                    + "조회된 카테고리가 없으면 빈 배열(`[]`)을 반환합니다."
    )
    @GetMapping
    public ResponseEntity<SuccessResponse<UserCategoryResponse>> getCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(value = "type", required = false) CategoryType type
    ) {
        Long userId = customUserDetails.getUserId();
        UserCategoryResponse userCategoryResponse = categoryService.getCategories(userId, type);

        SuccessResponse<UserCategoryResponse> successResponse = SuccessResponse.<UserCategoryResponse>builder()
                .status(SuccessCode.CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_FETCH_SUCCESS.getMessage())
                .data(userCategoryResponse)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 카테고리 초대장 조회 API
     */
    @Operation(
            summary = "카테고리 초대장 목록 조회",
            description = "사용자가 받은 그룹 카테고리 초대장을 상태별로 최신순으로 조회합니다.\n\n"
                    + "**초대장 상태**\n"
                    + "- `PENDING`: 대기 중\n"
                    + "- `ACCEPTED`: 수락됨\n"
                    + "- `REJECTED`: 거절됨\n\n"
                    + "해당 상태의 초대장이 없으면 빈 배열(`[]`)을 반환합니다."
    )
    @GetMapping("/invitations")
    public ResponseEntity<SuccessResponse<List<CategoryInvitationResponse>>> getCategoryInvitations(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(value = "status", defaultValue = "PENDING") CategoryInvitationStatus status
    ) {
        Long userId = customUserDetails.getUserId();
        List<CategoryInvitationResponse> invitations = categoryService.getCategoryInvitationsByStatus(userId, status);

        SuccessResponse<List<CategoryInvitationResponse>> successResponse = SuccessResponse.<List<CategoryInvitationResponse>>builder()
                .status(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(invitations)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 카테고리 상세 조회 API
     */
    @Operation(
            summary = "카테고리 상세 조회",
            description = "카테고리의 상세 정보를 조회합니다.\n\n"
                    + "**개인 카테고리**: 멤버 목록이 빈 배열(`[]`)로 반환됩니다."
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryDetailResponse>> getCategoryDetail(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        CategoryDetailResponse categoryDetailResponse = categoryService.getCategoryDetail(categoryId, userId);

        SuccessResponse<CategoryDetailResponse> successResponse = SuccessResponse.<CategoryDetailResponse>builder()
                .status(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getMessage())
                .data(categoryDetailResponse)
                .build();
        return ResponseEntity.ok(successResponse);
    }
}