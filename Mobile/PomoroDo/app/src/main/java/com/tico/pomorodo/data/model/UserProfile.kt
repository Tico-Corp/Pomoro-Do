package com.tico.pomorodo.data.model

data class UserProfile(
    val nickname: String,
    val profileImageUrl: String,
    val followingCount: Int,
    val followerCount: Int
)
