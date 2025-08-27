package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.UserInfoResponse

interface UserRemoteDataSource {
    suspend fun getUserInfo(): UserInfoResponse
}