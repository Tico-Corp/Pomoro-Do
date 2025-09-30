package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val groupMemberId: Int,
    val nickname: String,
    val profileImageUrl: String?
)

fun UserResponse.toUser() = User(
    id = groupMemberId,
    name = nickname,
    profileUrl = profileImageUrl
)