package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthApiService {
    @POST("/api/auth/google/login")
    suspend fun requestLogin(): AuthResponse

    @Multipart
    @POST("/api/auth/google/join")
    suspend fun requestJoin(
        @Part("value")
        member: RequestBody,
        @Part
        multipartFile: MultipartBody.Part?,
    ): AuthResponse
}