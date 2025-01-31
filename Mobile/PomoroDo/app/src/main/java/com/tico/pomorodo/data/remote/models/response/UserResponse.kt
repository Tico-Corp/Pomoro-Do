package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val email: String,
    val name: String,
    val profileUrl: String? = null
)

fun UserResponse.toUser() = User(id, email, name, profileUrl)