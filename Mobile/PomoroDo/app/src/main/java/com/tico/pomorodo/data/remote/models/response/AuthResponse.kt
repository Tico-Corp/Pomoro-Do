package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.Auth
import com.tico.pomorodo.data.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val status: Int,
    val message: String,
    val code: String,
    val data: TokenResponse
)

@Serializable
data class TokenResponse(val accessToken: String)

@Serializable
data class ErrorResponse(
    val status: Int,
    val code: String,
    val message: String
)

fun AuthResponse.asModel() = Auth(status = status, message = message, data = data.asModel())

fun TokenResponse.asModel() = Token(accessToken)