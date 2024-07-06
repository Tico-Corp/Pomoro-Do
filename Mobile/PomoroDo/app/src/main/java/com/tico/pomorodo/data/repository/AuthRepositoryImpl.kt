package com.tico.pomorodo.data.repository

import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager
) : AuthRepository {

    override fun saveToken(token: String) {
        preferencesManager.saveToken(token)
    }

    override fun getToken(): String? {
        return preferencesManager.getToken()
    }

    override fun clearToken() {
        preferencesManager.clearToken()
    }
}