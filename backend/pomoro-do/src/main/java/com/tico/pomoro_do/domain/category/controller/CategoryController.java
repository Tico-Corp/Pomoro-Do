package com.tico.pomoro_do.domain.category.controller;

import com.tico.pomoro_do.domain.category.dto.request.CategoryCreationDTO;
import com.tico.pomoro_do.domain.category.dto.response.*;
import com.tico.pomoro_do.domain.category.service.CategoryService;
import com.tico.pomoro_do.domain.user.entity.User;
import com.tico.pomoro_do.domain.user.service.UserService;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            @Valid @RequestBody CategoryCreationDTO request
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
            summary = "일반 / 그룹 / 초대받은 그룹 카테고리 조회",
            description = "현재 인증된 사용자의 오늘 날짜에 해당하는 일반 카테고리와 그룹 카테고리를 제목순으로 정렬하여 조회하고," +
                    " 아직 응답하지 않은 초대받은 그룹 카테고리를 최신순으로 조회합니다. <br>" +
                    "일반 / 그룹 / 초대받은 그룹 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
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
            summary = "오늘의 일반 카테고리 조회",
            description = "현재 인증된 사용자의 오늘 날짜에 해당하는 일반 카테고리를 제목순으로 정렬하여 조회합니다. <br>" +
                    "일반 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
    )
    @GetMapping("/general")
    public ResponseEntity<SuccessResponseDTO<List<GeneralCategoryDTO>>> getGeneralCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String username = customUserDetails.getUsername();
        User user = userService.findByUsername(username);
        List<GeneralCategoryDTO> categories = categoryService.getGeneralCategories(user);

        SuccessResponseDTO<List<GeneralCategoryDTO>> successResponse = SuccessResponseDTO.<List<GeneralCategoryDTO>>builder()
                .status(SuccessCode.GENERAL_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.GENERAL_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(categories)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "오늘의 그룹 카테고리 조회",
            description = "현재 인증된 사용자의 오늘 날짜에 해당하는 그룹 카테고리를 제목순으로 정렬하여 조회합니다. <br>" +
                    "그룹 카테고리가 없는 경우 각각 빈 배열([])을 반환합니다."
    )
    @GetMapping("/groups")
    public ResponseEntity<SuccessResponseDTO<List<GroupCategoryDTO>>> getGroupCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String username = customUserDetails.getUsername();
        User user = userService.findByUsername(username);
        List<GroupCategoryDTO> categories = categoryService.getGroupCategories(user);

        SuccessResponseDTO<List<GroupCategoryDTO>> successResponse = SuccessResponseDTO.<List<GroupCategoryDTO>>builder()
                .status(SuccessCode.GROUP_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.GROUP_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(categories)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "응답하지 않은 초대받은 그룹 카테고리 조회",
            description = "현재 인증된 사용자가 아직 응답하지 않은 초대받은 그룹 카테고리를 최신순으로 조회합니다. <br>" +
                    "응답하지 않은 초대 그룹 카테고리가 없는 경우 빈 배열([])을 반환합니다."
    )
    @GetMapping("/groups/invitations")
    public ResponseEntity<SuccessResponseDTO<List<InvitedGroupDTO>>> getInvitedGroupCategories(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String username = customUserDetails.getUsername();
        User user = userService.findByUsername(username);
        List<InvitedGroupDTO> groupCategories = categoryService.getInvitedGroupCategories(user);

        SuccessResponseDTO<List<InvitedGroupDTO>> successResponse = SuccessResponseDTO.<List<InvitedGroupDTO>>builder()
                .status(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.INVITED_CATEGORY_FETCH_SUCCESS.getMessage())
                .data(groupCategories)
                .build();
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "카테고리 상세 정보 조회",
            description = "카테고리 상세 정보를 조회합니다. <br>" +
                    "일반 카테고리는 멤버에 빈 배열([])을 반환합니다."
    )
    @GetMapping("/{categoryId}")
    public ResponseEntity<SuccessResponseDTO<CategoryDetailDTO>> getCategoryDetail(
            @PathVariable Long categoryId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        String username = customUserDetails.getUsername();
        CategoryDetailDTO categoryDetailDTO = categoryService.getCategoryDetail(categoryId, username);

        SuccessResponseDTO<CategoryDetailDTO> successResponse = SuccessResponseDTO.<CategoryDetailDTO>builder()
                .status(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.CATEGORY_DETAIL_FETCH_SUCCESS.getMessage())
                .data(categoryDetailDTO)
                .build();
        return ResponseEntity.ok(successResponse);
    }

}
