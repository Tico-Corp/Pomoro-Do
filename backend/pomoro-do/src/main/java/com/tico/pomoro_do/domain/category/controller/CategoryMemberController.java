package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryLeaveRequest;
import com.tico.pomoro_do.domain.category.service.CategoryMemberService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category Member: 그룹 카테고리 멤버", description = "그룹 카테고리 멤버 관련 API")
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryMemberController {

    private final CategoryMemberService categoryMemberService;

    /**
     * 그룹 카테고리에서 사용자 탈퇴 처리
     *
     * - 일반 멤버는 즉시 탈퇴 처리됩니다.
     * - 그룹장은 위임 가능한 멤버가 있는 경우 자동 위임 후 탈퇴됩니다.
     * - 그룹장 단독인 경우 탈퇴할 수 없으며, 별도 삭제 API를 호출해야 합니다.
     *
     * @param categoryId 카테고리 ID
     * @param request 탈퇴 시 삭제 정책
     */
    @Operation(
            summary = "그룹 카테고리 나가기",
            description = """
                    그룹 카테고리에서 현재 사용자를 탈퇴 처리합니다.

                    - 일반 멤버: 즉시 탈퇴 처리됩니다.
                    - 그룹장:
                      - 다른 멤버가 존재할 경우 → 다음 멤버(닉네임 오름차순)에게 자동으로 그룹장 권한이 위임된 후 탈퇴됩니다.
                      - ⚠️ 본인만 남은 경우 → 탈퇴할 수 없으며, 그룹 삭제 API를 별도로 호출해야 합니다.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "그룹 나가기 성공"),
            @ApiResponse(responseCode = "400", description = "그룹 카테고리가 아닙니다."),
            @ApiResponse(responseCode = "403", description = "마지막 그룹장은 탈퇴할 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "그룹 멤버를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "409", description = "이미 탈퇴한 멤버입니다."),
            @ApiResponse(responseCode = "500", description = "그룹장 위임에 실패했습니다.")
    })
    @PostMapping("/{categoryId}/leave")
    public ResponseEntity<SuccessResponse<String>> leaveGroupCategory(
            @PathVariable Long categoryId,
            @RequestBody @Valid CategoryLeaveRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        categoryMemberService.leaveGroupCategory(userDetails.getUserId(), categoryId, request);

        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .message(SuccessCode.LEAVE_GROUP_CATEGORY_SUCCESS.getMessage())
                .data(SuccessCode.LEAVE_GROUP_CATEGORY_SUCCESS.name())
                .build();
        return ResponseEntity.ok(successResponse);
    }
}