package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.domain.user.entity.User;
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
            description = "현재 인증된 사용자의 카테고리를 생성합니다. <br><br>" +
                    "<b>카테고리 유형</b>: <br>" +
                    "- `PERSONAL`: 개인 카테고리 <br>" +
                    "- `GROUP`: 그룹 카테고리 (멤버 초대 가능) <br><br>" +
                    "<b>공개 설정</b>: <br>" +
                    "- `PUBLIC`: 전체 공개 <br>" +
                    "- `FOLLOWERS`: 팔로워에게만 공개 <br>" +
                    "- `PRIVATE`: 비공개 <br>" +
                    "- `GROUP`: 그룹 멤버에게만 공개 <br><br>" +
                    "그룹 카테고리 생성 시, 요청자는 자동으로 소유자(OWNER) 권한으로 등록되며 " +
                    "요청에 포함된 멤버 ID 목록으로 초대장이 발송됩니다."
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

    @Operation(
            summary = "개인 / 그룹 / 초대받은 그룹 카테고리 조회",
            description = "현재 인증된 사용자의 오늘 날짜에 해당하는 개인 카테고리와 그룹 카테고리를 이름순으로 정렬하여 조회하고," +
                    " 아직 응답하지 않은 초대받은 그룹 카테고리를 최신순으로 조회합니다. <br>" +
                    "개인 / 그룹 / 초대받은 그룹 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
    )
    @GetMapping
    public ResponseEntity<SuccessResponse<UserCategoryResponse>> getCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long userId = customUserDetails.getUserId();
        UserCategoryResponse userCategoryResponse = categoryService.getCategories(userId);

        SuccessResponse<UserCategoryResponse> successResponse = SuccessResponse.<UserCategoryResponse>builder()
                .status(SuccessCode.CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_FETCH_SUCCESS.getMessage())
                .data(userCategoryResponse)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "오늘의 개인 카테고리 조회",
            description = "현재 인증된 사용자의 오늘 날짜에 해당하는 개인 카테고리를 이름순으로 정렬하여 조회합니다. <br>" +
                    "개인 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
    )
    @GetMapping("/personal")
    public ResponseEntity<SuccessResponse<List<PersonalCategoryResponse>>> getGeneralCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserById(customUserDetails.getUserId());
        List<PersonalCategoryResponse> categories = categoryService.getPersonalCategories(user);

        SuccessResponse<List<PersonalCategoryResponse>> successResponse = SuccessResponse.<List<PersonalCategoryResponse>>builder()
                .status(SuccessCode.GENERAL_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.GENERAL_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(categories)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "오늘의 그룹 카테고리 조회",
            description = "현재 인증된 사용자의 오늘 날짜에 해당하는 그룹 카테고리를 이름순으로 정렬하여 조회합니다. <br>" +
                    "그룹 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
    )
    @GetMapping("/groups")
    public ResponseEntity<SuccessResponse<List<GroupCategoryResponse>>> getGroupCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        User user = userService.findUserById(customUserDetails.getUserId());
        List<GroupCategoryResponse> categories = categoryService.getGroupCategories(user);

        SuccessResponse<List<GroupCategoryResponse>> successResponse = SuccessResponse.<List<GroupCategoryResponse>>builder()
                .status(SuccessCode.GROUP_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.GROUP_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(categories)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "그룹 카테고리 초대장 조회",
            description = "현재 인증된 사용자가 상태별로 그룹 카테고리 초대장을 조회합니다. <br>" +
                    "초대장 상태(`status`)는 `PENDING`(대기 중), `ACCEPTED`(수락됨), `REJECTED`(거절됨) 등으로 조회할 수 있습니다. <br>" +
                    "해당 상태에 해당하는 초대장이 없으면, 빈 배열([])을 반환합니다."
    )
    @GetMapping("/invitations")
    public ResponseEntity<SuccessResponse<List<CategoryInvitationResponse>>> getCategoryInvitations(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(value = "status", defaultValue = "PENDING") CategoryInvitationStatus status
    ) {
        User user = userService.findUserById(customUserDetails.getUserId());
        List<CategoryInvitationResponse> invitations = categoryService.getCategoryInvitations(user, status);

        SuccessResponse<List<CategoryInvitationResponse>> successResponse = SuccessResponse.<List<CategoryInvitationResponse>>builder()
                .status(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(invitations)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "카테고리 상세 정보 조회",
            description = "카테고리 상세 정보를 조회합니다. <br>" +
                    "개인 카테고리는 멤버에 빈 배열([])을 반환합니다."
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryDetailResponse>> getCategoryDetail(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String email = customUserDetails.getUsername();
        CategoryDetailResponse categoryDetailResponse = categoryService.getCategoryDetail(categoryId, email);

        SuccessResponse<CategoryDetailResponse> successResponse = SuccessResponse.<CategoryDetailResponse>builder()
                .status(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getMessage())
                .data(categoryDetailResponse)
                .build();
        return ResponseEntity.ok(successResponse);
    }

}
