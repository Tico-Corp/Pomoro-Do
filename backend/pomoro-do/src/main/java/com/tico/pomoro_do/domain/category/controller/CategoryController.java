package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreateRequest;
import com.tico.pomoro_do.domain.category.dto.request.CategoryDeleteRequest;
import com.tico.pomoro_do.domain.category.dto.request.CategoryUpdateRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryDetailResponse;
import com.tico.pomoro_do.domain.category.dto.response.UserCategoryResponse;
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
            summary = "사용자 카테고리 목록 조회 (필터 가능)",
            description = """
                    인증된 사용자의 카테고리 목록을 조회합니다.
            
                    ## 조회 동작
                    - 기본: 개인 카테고리 + 그룹 카테고리 + 수락 대기 중인 초대장 포함
                    - `type` 파라미터 지정 시: 해당 유형만 필터링
            
                    ## type 파라미터 값
                    - `PERSONAL`: 개인 카테고리만 조회
                    - `GROUP`: 그룹 카테고리만 조회
                    - 생략(null): 전체 카테고리 조회 + 초대장 포함
            
                    ## 정렬 기준
                    - 개인/그룹 카테고리: 이름순 (가나다)
                    - 초대장 목록: 초대 생성일 기준 내림차순
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
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId
    ) {
        CategoryDetailResponse response = categoryService.getCategoryDetail(categoryId, userDetails.getUserId());

        SuccessResponse<CategoryDetailResponse> successResponse = SuccessResponse.<CategoryDetailResponse>builder()
                .message(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getMessage())
                .data(response)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 카테고리 수정 API
     *
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @param categoryId 수정할 카테고리 ID
     * @param request 카테고리 수정 정보
     * @return 카테고리 수정 성공 메시지
     */
    @Operation(
            summary = "카테고리 수정",
            description = """
                    사용자가 소유한 카테고리를 수정합니다.
                    
                    [일반 카테고리]
                    - 이름(name), 공개 범위(visibility) 수정 가능
                    - visibility 예시: PRIVATE, FOLLOWERS, PUBLIC
                    
                    [그룹 카테고리]
                    - 이름만 수정 가능
                    - 공개 범위는 GROUP으로 고정 (수정 시 예외 발생)
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "카테고리 수정 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식 또는 삭제된 카테고리"),
                    @ApiResponse(responseCode = "403", description = "수정 권한 없음"),
                    @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없음")
            }
    )
    @PatchMapping("/api/v1/categories/{categoryId}")
    public ResponseEntity<SuccessResponse<String>> updateCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryUpdateRequest request) {

        categoryService.updateCategory(categoryId, userDetails.getUserId(), request);

        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .message(SuccessCode.CATEGORY_UPDATE_SUCCESS.getMessage())
                .data(SuccessCode.CATEGORY_UPDATE_SUCCESS.name())
                .build();

        return ResponseEntity.ok(successResponse);
    }


    /**
     * 카테고리 삭제 API
     *
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @param categoryId 삭제할 카테고리 ID
     * @param request 카테고리 삭제 정책
     * @return 카테고리 삭제 성공
     */
    @Operation(
            summary = "카테고리 삭제",
            description = """
                    카테고리 소유자가 카테고리를 삭제합니다.
                    
                    - 일반/그룹 카테고리 모두 삭제할 수 있으며, 삭제 정책에 따라 할 일 상태를 처리합니다.
                    - 그룹 카테고리는 모든 멤버의 탈퇴 처리 및 초대장 삭제가 함께 이루어집니다.
                    - 삭제는 논리 삭제 방식이며, 삭제 정책은 요청 본문에 포함되어야 합니다.
                    
                    ⚠️ 그룹의 마지막 멤버(소유자)는 탈퇴가 불가능하며, 이 API를 별도로 호출해야 합니다.
                    """
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리 삭제에 성공했습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 이미 삭제된 카테고리입니다."),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없습니다 (소유자 아님)."),
            @ApiResponse(responseCode = "404", description = "카테고리를 찾을 수 없습니다.")
    })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<SuccessResponse<String>> deleteCategory(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryDeleteRequest request
    ) {
        categoryService.deleteCategory(userDetails.getUserId(), categoryId, request);

        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .message(SuccessCode.CATEGORY_DELETION_SUCCESS.getMessage())
                .data(SuccessCode.CATEGORY_DELETION_SUCCESS.name())
                .build();
        return ResponseEntity.ok(successResponse);
    }
}