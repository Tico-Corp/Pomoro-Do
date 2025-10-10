package com.tico.pomorodo.data.remote.models.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val message: String,
    val code: String,
    val data: T
)