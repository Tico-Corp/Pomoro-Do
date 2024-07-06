package com.tico.pomorodo.domain.repository

interface AuthRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}