package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.Base
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Int,
    val message: String,
    val code: String,
    val data: T
)

fun <T, R> BaseResponse<T>.toBase(mapper: (T) -> R): Base<R> =
    Base(message = message, data = mapper(data))