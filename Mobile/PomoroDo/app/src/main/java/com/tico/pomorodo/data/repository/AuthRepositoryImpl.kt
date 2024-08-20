package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.PreferencesManager
import com.tico.pomorodo.data.model.Base
import com.tico.pomorodo.data.model.Token
import com.tico.pomorodo.data.remote.datasource.AuthDataSource
import com.tico.pomorodo.data.remote.models.response.toBase
import com.tico.pomorodo.domain.model.ProfileImageType
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun saveRefreshToken(token: String) {
        preferencesManager.saveRefreshToken(token)
    }

    override suspend fun saveAccessToken(token: String) {
        preferencesManager.saveAccessToken(token)
    }

    override suspend fun isAccessToken(): Boolean {
        return preferencesManager.isAccessToken()
    }

    override suspend fun getAccessToken(): String? {
        return preferencesManager.getAccessToken()
    }

    override suspend fun clearAccessToken() {
        preferencesManager.clearAccessToken()
    }

    override suspend fun saveIdToken(token: String) {
        preferencesManager.saveIdToken(token)
    }

    override suspend fun getIdToken(): String? {
        return preferencesManager.getIdToken()
    }

    override suspend fun clearIdToken() {
        preferencesManager.clearIdToken()
    }

    override suspend fun saveFID(fid: String) {
        preferencesManager.saveFID(fid)
    }

    override suspend fun getFID(): String? {
        return preferencesManager.getFID()
    }

    override suspend fun requestLogin(): Flow<Resource<Base<Token>>> = flow {
        emit(Resource.Loading)
        val data = wrapToResource(Dispatchers.IO) {
            val response = authDataSource.requestLogin()
            response.toBase { tokenResponse ->
                Token(
                    accessToken = tokenResponse.accessToken,
                    refreshToken = tokenResponse.refreshToken
                )
            }
        }
        emit(data)
    }.flowOn(Dispatchers.IO)


    override suspend fun requestJoin(
        name: String,
        profile: File?,
        profileImageType: ProfileImageType
    ): Flow<Resource<Base<Token>>> =
        flow {
            emit(Resource.Loading)
            val data = wrapToResource(Dispatchers.IO) {
                val response =
                    authDataSource.requestJoin(name, profile, profileImageType)
                response.toBase { tokenResponse ->
                    Token(
                        accessToken = tokenResponse.accessToken,
                        refreshToken = tokenResponse.refreshToken
                    )
                }
            }
            emit(data)
        }.flowOn(Dispatchers.IO)
}