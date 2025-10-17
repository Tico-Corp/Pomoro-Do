package com.tico.pomorodo.data.remote.models.response.user

import com.tico.pomorodo.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val userId: Int,
    val nickname: String,
    val profileImageUrl: String?
)

fun UserResponse.toUser() = User(
    id = userId,
    name = nickname,
    profileUrl = profileImageUrl
)