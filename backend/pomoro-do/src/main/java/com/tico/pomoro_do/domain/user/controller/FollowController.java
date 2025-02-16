package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.response.UserProfileResponse;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.domain.auth.security.CustomUserDetails;
import com.tico.pomoro_do.global.code.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponse;
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

@Tag(name = "Follow: 팔로우", description = "팔로우 관련 API")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class FollowController {

    private final FollowService followService;

    /**
     * 현재 인증된 사용자가 다른 사용자를 팔로우하는 API
     *
     * @param customUserDetails 현재 인증된 사용자의 정보
     * @param userId 팔로우 당하는 사용자 ID
     * @return 성공 시 성공 메시지를 포함한 SuccessResponse 반환
     */
    @Operation(
            summary = "현재 사용자가 특정 사용자를 팔로우",
            description = "현재 인증된 사용자가 특정 사용자를 팔로우합니다. <br>" +
                    "팔로우가 성공하면 성공 메시지를 반환합니다. " +
                    "팔로우할 사용자가 존재하지 않거나 이미 팔로우 중인 경우 예외가 발생할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "성공적으로 팔로우됨"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 팔로우 중인 사용자")
    })
    @PostMapping("/{userId}/follow")
    public ResponseEntity<SuccessResponse<String>> followUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId
    ) {
        // 현재 로그인한 사용자 ID
        Long currentUserId = customUserDetails.getUserId();
        followService.followUser(currentUserId, userId);
        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .status(SuccessCode.FOLLOW_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.FOLLOW_SUCCESS.getMessage())
                .data(SuccessCode.FOLLOW_SUCCESS.name())
                .build();
        return ResponseEntity.ok(successResponse);
    }

    /**
     * 현재 인증된 사용자가 팔로우하는 특정 계정을 팔로우 취소합니다.
     *
     * @param customUserDetails 현재 인증된 사용자의 정보
     * @param userId 팔로우 취소할 사용자의 ID
     * @return 성공 시 성공 메시지를 포함한 SuccessResponse 반환
     */
    @Operation(
            summary = "현재 사용자가 특정 사용자를 팔로우 취소",
            description = "현재 인증된 사용자가 팔로우하는 특정 사용자를 팔로우 취소합니다."
    )
    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<SuccessResponse<String>> unfollowUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId
    ){
        Long currentUserId = customUserDetails.getUserId();
        followService.unfollowUser(currentUserId, userId);
        SuccessResponse<String> successResponse = SuccessResponse.<String>builder()
                .status(SuccessCode.UNFOLLOW_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.UNFOLLOW_SUCCESS.getMessage())
                .data(SuccessCode.UNFOLLOW_SUCCESS.name())
                .build();

        return ResponseEntity.ok(successResponse);
    }

    /**
     * 현재 사용자를 팔로우 중인 사용자 목록을 조회하는 API
     *
     * @param customUserDetails 현재 인증된 사용자의 정보
     * @return 사용자를 팔로우 중인 사용자 목록을 포함한 List<UserProfileResponse> 반환
     */
    @Operation(
            summary = "현재 사용자의 팔로워 목록 조회 (내 팔로워 조회)",
            description = "현재 인증된 사용자를 팔로우 중인 사용자 목록을 조회합니다. <br>" +
                    "성공적으로 조회되면 팔로워 정보를 포함한 리스트를 닉네임 기준으로 오름차순 정렬하여 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/me/followers")
    public ResponseEntity<SuccessResponse<List<UserProfileResponse>>> getFollowers(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        Long currentUserId = customUserDetails.getUserId();
        List<UserProfileResponse> followersList = followService.getFollowers(currentUserId);

        SuccessResponse<List<UserProfileResponse>> successResponse = SuccessResponse.<List<UserProfileResponse>>builder()
                .status(SuccessCode.FOLLOWERS_LIST_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.FOLLOWERS_LIST_FETCH_SUCCESS.getMessage())
                .data(followersList)
                .build();

        return ResponseEntity.ok(successResponse);
    }

    /**
     * 사용자가 팔로우 중인 사용자 목록을 조회하는 API
     *
     * @param customUserDetails 현재 인증된 사용자의 정보
     * @return 사용자가 팔로우 중인 사용자 목록을 포함한 List<UserProfileResponse> 반환
     */
    @Operation(
            summary = "현재 사용자의 팔로우 목록 조회 (내 팔로잉 조회)",
            description = "현재 인증된 사용자가 팔로우 중인 사용자 목록을 조회합니다. <br>" +
                    "성공적으로 조회되면 팔로우한 사용자 정보를 포함한 리스트를 닉네임 기준으로 오름차순 정렬하여 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/me/followings")
    public ResponseEntity<SuccessResponse<List<UserProfileResponse>>> getFollowings(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        Long currentUserId = customUserDetails.getUserId();
        List<UserProfileResponse> followingList = followService.getFollowings(currentUserId);

        SuccessResponse<List<UserProfileResponse>> successResponse = SuccessResponse.<List<UserProfileResponse>>builder()
                .status(SuccessCode.FOLLOWING_LIST_FETCH_SUCCESS.getHttpStatus().value())
                .message(SuccessCode.FOLLOWING_LIST_FETCH_SUCCESS.getMessage())
                .data(followingList)
                .build();

        return ResponseEntity.ok(successResponse);
    }
}
