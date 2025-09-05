package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.UserInfoResponse
import retrofit2.http.GET

interface UserApiService {
    @GET("v1/users/me")
    suspend fun getUserInfo(): UserInfoResponse
}