package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.Base
import com.tico.pomorodo.data.model.Token
import com.tico.pomorodo.domain.model.ProfileImageType
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AuthRepository {
    suspend fun saveRefreshToken(token: String)
    suspend fun saveAccessToken(token: String)
    suspend fun isAccessToken(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun clearAccessToken()
    suspend fun saveIdToken(token: String)
    suspend fun getIdToken(): String?
    suspend fun clearIdToken()
    suspend fun saveFID(fid: String)
    suspend fun getFID(): String?
    suspend fun requestLogin(): Flow<Resource<Base<Token>>>
    suspend fun requestJoin(
        name: String,
        profile: File?,
        profileImageType: ProfileImageType
    ): Flow<Resource<Base<Token>>>
}