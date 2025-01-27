package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.UserEntity

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val profileUrl: String? = null
)

fun User.toUserEntity() = UserEntity(id, email, name, profileUrl)