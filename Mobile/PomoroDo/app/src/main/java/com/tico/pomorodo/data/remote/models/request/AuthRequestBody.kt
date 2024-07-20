package com.tico.pomorodo.data.remote.models.request

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRequestBody(
    val nickname: String,
)