package com.tico.pomorodo.data.model

data class Auth(val status: Int, val message: String, val data: Token)
data class Token(val accessToken: String, val refreshToken: String)