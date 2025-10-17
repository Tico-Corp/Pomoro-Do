package com.tico.pomorodo.data.remote.models.response.user

import com.tico.pomorodo.data.model.UserProfile
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val userId: Int,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val followingCount: Int,
    val followerCount: Int
)

fun UserInfoResponse.toUserProfile() = UserProfile(
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    followingCount = followingCount,
    followerCount = followerCount
)