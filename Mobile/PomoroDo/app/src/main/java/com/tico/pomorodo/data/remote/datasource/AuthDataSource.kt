package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.UserInfoRequestBody
import com.tico.pomorodo.data.remote.models.response.AuthResponse
import java.io.File

interface AuthDataSource {
    suspend fun requestLogin(): AuthResponse
    suspend fun requestJoin(userInfo: UserInfoRequestBody, image: File?): AuthResponse
}