package com.tico.pomorodo.data.remote.models.response

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String
)