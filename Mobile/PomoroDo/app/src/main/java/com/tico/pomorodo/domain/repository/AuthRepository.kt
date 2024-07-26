package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.Auth
import com.tico.pomorodo.data.remote.models.request.UserInfoRequestBody
import com.tico.pomorodo.domain.model.Resource
import java.io.File

interface AuthRepository {
    suspend fun saveAccessToken(token: String)
    suspend fun isAccessToken(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun clearAccessToken()
    suspend fun saveIdToken(token: String)
    suspend fun getIdToken(): String?
    suspend fun clearIdToken()
    suspend fun requestLogin(): Resource<Auth>
    suspend fun requestJoin(member: UserInfoRequestBody, image: File?): Resource<Auth>
}