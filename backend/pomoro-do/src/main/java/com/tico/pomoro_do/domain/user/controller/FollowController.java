package com.tico.pomoro_do.domain.user.controller;

import com.tico.pomoro_do.domain.user.dto.request.FollowRequest;
import com.tico.pomoro_do.domain.user.dto.response.UserProfileResponse;
import com.tico.pomoro_do.domain.user.service.FollowService;
import com.tico.pomoro_do.global.response.SuccessCode;
import com.tico.pomoro_do.global.response.SuccessResponse;
import com.tico.pomoro_do.global.security.auth.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Follow: 팔로우", description = "팔로우 관련 API")
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(
            summary = "사용자 팔로우",
            description = "내가 특정 사용자를 팔로우합니다. <br>" +
                    "팔로우할 사용자가 존재하지 않거나 이미 팔로우 중인 경우 예외가 발생할 수 있습니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "팔로우 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음"),
            @ApiResponse(responseCode = "409", description = "이미 팔로우 중")
    })
    @PostMapping("/me/following")
    public ResponseEntity<SuccessResponse<String>> followUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Valid@RequestBody FollowRequest request
    ) {
        followService.followUser(customUserDetails.getUserId(), request.getTargetUserId());
        SuccessResponse<String> successResponse = SuccessResponse.of(SuccessCode.FOLLOW_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    @Operation(
            summary = "사용자 언팔로우",
            description = "내가 팔로우 중인 특정 사용자를 팔로우 취소합니다."
    )
    @DeleteMapping("/me/following/{targetUserId}")
    public ResponseEntity<SuccessResponse<String>> unfollowUser(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Parameter(description = "언팔로우할 사용자 ID", required = true)
            @PathVariable Long targetUserId
    ){
        followService.unfollowUser(customUserDetails.getUserId(), targetUserId);
        SuccessResponse<String> successResponse = SuccessResponse.of(SuccessCode.UNFOLLOW_SUCCESS);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "내 팔로잉 목록 조회",
            description = "내가 팔로우하는 사용자 목록을 조회합니다. <br>" +
                    "성공적으로 조회되면 팔로우한 사용자 정보를 포함한 리스트를 닉네임 기준으로 오름차순 정렬하여 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로우 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/me/following")
    public ResponseEntity<SuccessResponse<List<UserProfileResponse>>> getMyFollowing(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        List<UserProfileResponse> followingList = followService.getFollowings(customUserDetails.getUserId());
        SuccessResponse<List<UserProfileResponse>> successResponse = SuccessResponse.of(SuccessCode.FOLLOWING_LIST_FETCH_SUCCESS, followingList);
        return ResponseEntity.ok(successResponse);
    }

    @Operation(
            summary = "내 팔로워 목록 조회",
            description = "나를 팔로우하는 사용자 목록을 조회합니다. <br>" +
                    "성공적으로 조회되면 팔로워 정보를 포함한 리스트를 닉네임 기준으로 오름차순 정렬하여 반환합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "팔로워 목록 조회 성공"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/me/followers")
    public ResponseEntity<SuccessResponse<List<UserProfileResponse>>> getMyFollowers(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ){
        List<UserProfileResponse> followersList = followService.getFollowers(customUserDetails.getUserId());
        SuccessResponse<List<UserProfileResponse>> successResponse = SuccessResponse.of(SuccessCode.FOLLOWERS_LIST_FETCH_SUCCESS, followersList);
        return ResponseEntity.ok(successResponse);
    }
}
