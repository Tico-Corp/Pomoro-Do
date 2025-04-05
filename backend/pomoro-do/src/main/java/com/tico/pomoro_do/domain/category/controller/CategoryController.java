package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDetailResponse;
import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.dto.response.UserCategoryResponse;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.enums.CategoryType;
import com.tico.pomoro_do.domain.category.service.CategoryService;
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

@Tag(name = "Category: 카테고리", description = "카테고리 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 카테고리 생성 API
     *
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @param request 카테고리 생성 요청 DTO
     * @return 생성된 카테고리 ID
     */
    @Operation(
            summary = "카테고리 생성",
            description = """
                    사용자의 카테고리를 생성합니다.
            
                    ## 카테고리 유형
                    - `PERSONAL`: 개인 카테고리
                    - `GROUP`: 그룹 카테고리
            
                    ## 공개 범위
                    - `PUBLIC`: 전체 공개
                    - `FOLLOWERS`: 팔로워에게만 공개
                    - `PRIVATE`: 비공개
                    - `GROUP`: 그룹 멤버에게만 공개
            
                    ### 그룹 카테고리 특이사항
                    - 요청자는 자동으로 `OWNER` 권한 부여
                    - 지정된 멤버에게 초대장 발송
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "카테고리 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 - 입력 데이터 유효성 검증 실패"),
            @ApiResponse(responseCode = "401", description = "인증 오류 - 로그인 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음 - 카테고리 생성 권한 부족"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @PostMapping
    public ResponseEntity<SuccessResponse<Long>> createCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CategoryCreateRequest request
    ){
        Long categoryId = categoryService.processCategoryCreation(userDetails.getUserId(), request);

        SuccessResponse<Long> successResponse = SuccessResponse.<Long>builder()
                .message(SuccessCode.CATEGORY_CREATION_SUCCESS.getMessage())
                .data(categoryId)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }


    /**
     * 카테고리 목록 조회 API
     *
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @param type 조회할 카테고리 유형 (선택)
     * @return 사용자 카테고리 목록
     */
    @Operation(
            summary = "카테고리 목록 조회",
            description = """
                    사용자의 카테고리 목록을 조회합니다.
            
                    ## 조회 유형
                    - 미입력(생략): 전체 카테고리 조회 (기본값)
                    - `PERSONAL`: 개인 카테고리만 조회
                    - `GROUP`: 그룹 카테고리만 조회
            
                    ## 정렬 기준
                    - 개인/그룹 카테고리: 이름순 정렬
                    - 초대받은 카테고리: 최근 초대순 정렬
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 오류 - 로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping
    public ResponseEntity<SuccessResponse<UserCategoryResponse>> getCategories(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "type", required = false) CategoryType type
    ) {
        UserCategoryResponse response = categoryService.getCategories(userDetails.getUserId(), type);

        SuccessResponse<UserCategoryResponse> successResponse = SuccessResponse.<UserCategoryResponse>builder()
                .message(SuccessCode.CATEGORY_FETCH_SUCCESS.getMessage())
                .data(response)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 카테고리 초대장 조회 API
     *
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @param status 초대장 상태 (기본값: PENDING)
     * @return 초대장 목록
     */
    @Operation(
            summary = "카테고리 초대장 조회",
            description = """
                    사용자가 받은 그룹 카테고리 초대장을 상태별로 조회합니다.
                            
                    ## 초대장 상태
                    - `PENDING`: 대기 중인 초대장
                    - `ACCEPTED`: 수락된 초대장
                    - `REJECTED`: 거절된 초대장
            
                    ### 조회 특이사항
                    - 기본값: 대기 중인 초대장 (`PENDING`)
                    - 최신 순서로 정렬
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "초대장 목록 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 오류 - 로그인 필요"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/invitations")
    public ResponseEntity<SuccessResponse<List<CategoryInvitationResponse>>> getCategoryInvitations(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "status", defaultValue = "PENDING") CategoryInvitationStatus status
    ) {
        List<CategoryInvitationResponse> response = categoryService.getCategoryInvitationsByStatus(userDetails.getUserId(), status);

        SuccessResponse<List<CategoryInvitationResponse>> successResponse = SuccessResponse.<List<CategoryInvitationResponse>>builder()
                .message(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(response)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 카테고리 상세 조회 API
     *
     * @param categoryId 조회할 카테고리 ID
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @return 카테고리 상세 정보
     */
    @Operation(
            summary = "카테고리 상세 정보 조회",
            description = """
                    특정 카테고리의 상세 정보를 조회합니다.
            
                    ## 카테고리 유형별 차이점
                    - 개인 카테고리: 멤버 목록 미포함 (빈 배열)
                    - 그룹 카테고리: 멤버 목록 포함
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 상세 정보 조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증 오류 - 로그인 필요"),
            @ApiResponse(responseCode = "403", description = "권한 없음 - 카테고리 접근 권한 부족"),
            @ApiResponse(responseCode = "404", description = "카테고리 정보 없음"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryDetailResponse>> getCategoryDetail(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        CategoryDetailResponse response = categoryService.getCategoryDetail(categoryId, userDetails.getUserId());

        SuccessResponse<CategoryDetailResponse> successResponse = SuccessResponse.<CategoryDetailResponse>builder()
                .message(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getMessage())
                .data(response)
                .build();
        return ResponseEntity.ok(successResponse);
    }
}