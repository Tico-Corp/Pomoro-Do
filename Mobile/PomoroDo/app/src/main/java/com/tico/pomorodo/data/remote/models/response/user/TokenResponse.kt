package com.tico.pomorodo.data.remote.models.response.user

import com.tico.pomorodo.data.model.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(val accessToken: String, val refreshToken: String)

fun TokenResponse.toToken() = Token(accessToken, refreshToken)