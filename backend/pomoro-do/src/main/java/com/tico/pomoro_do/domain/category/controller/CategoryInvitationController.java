package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryInvitationDecisionRequest;
import com.tico.pomoro_do.domain.category.dto.response.CategoryInvitationResponse;
import com.tico.pomoro_do.domain.category.enums.CategoryInvitationStatus;
import com.tico.pomoro_do.domain.category.service.CategoryInvitationService;
import com.tico.pomoro_do.global.response.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponse;
import com.tico.pomoro_do.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category Invitation: 그룹 초대", description = "그룹 카테고리 초대 관련 API")
@RestController
@RequestMapping("/api/v1/categories/invitations")
@RequiredArgsConstructor
@Slf4j
public class CategoryInvitationController {

    private final CategoryInvitationService categoryInvitationService;

    /**
     * 카테고리 초대장 조회 API
     *
     * @param userDetails 인증된 사용자 정보 (CustomUserDetails)
     * @param status 초대장 상태 (기본값: PENDING)
     * @return 초대장 목록
     */
    @Operation(
            summary = "카테고리 초대장 상태별 조회",
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
    @GetMapping
    public ResponseEntity<SuccessResponse<List<CategoryInvitationResponse>>> getCategoryInvitations(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(value = "status", defaultValue = "PENDING") CategoryInvitationStatus status
    ) {
        List<CategoryInvitationResponse> response = categoryInvitationService.getInvitationsByStatus(userDetails.getUserId(), status);

        SuccessResponse<List<CategoryInvitationResponse>> successResponse = SuccessResponse.<List<CategoryInvitationResponse>>builder()
                .message(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(response)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @PatchMapping("/{invitationId}")
    public ResponseEntity<SuccessResponse<String>> responseInvitation(
            @PathVariable Long invitationId,
            @RequestBody CategoryInvitationDecisionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails // 로그인 유저
    ) {
        categoryInvitationService.respondInvitation(invitationId, userDetails.getUserId(), request);

        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .message(SuccessCode.CATEGORY_INVITATION_RESPONSE_SUCCESS.getMessage())
                .data(SuccessCode.CATEGORY_INVITATION_RESPONSE_SUCCESS.name())
                .build();
        return ResponseEntity.ok(successResponse);

    }
}