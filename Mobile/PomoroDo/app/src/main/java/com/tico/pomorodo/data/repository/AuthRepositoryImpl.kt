package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.data.model.Auth
import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.remote.models.request.UserInfoRequestBody
import com.tico.pomorodo.data.remote.models.response.asModel
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun saveAccessToken(token: String) {
        preferencesManager.saveAccessToken(token)
    }

    override suspend fun getAccessToken(): String {
        return preferencesManager.getAccessToken()
    }

    override suspend fun clearAccessToken() {
        preferencesManager.clearAccessToken()
    }

    override suspend fun saveIdToken(token: String) {
        preferencesManager.saveIdToken(token)
    }

    override suspend fun getIdToken(): String {
        return preferencesManager.getIdToken()
    }

    override suspend fun clearIdToken() {
        preferencesManager.clearIdToken()
    }

    override suspend fun requestLogin(): Resource<Auth> =
        wrapToResource(Dispatchers.IO) {
            authDataSource.requestLogin().asModel()
        }

    override suspend fun requestJoin(member: UserInfoRequestBody, image: File?): Resource<Auth> =
        wrapToResource(Dispatchers.IO) {
            authDataSource.requestJoin(member, image).asModel()
        }
}