package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.TokenResponse
import com.tico.pomorodo.domain.model.ProfileImageType
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AuthApiService {
    @POST("v1/auth/google/login")
    suspend fun requestLogin(): BaseResponse<TokenResponse>

    @Multipart
    @POST("v1/auth/google/signup")
    suspend fun requestJoin(
        @Query("nickname")
        nickname: String,
        @Part
        multipartFile: MultipartBody.Part?,
        @Query("profileImageType")
        profileImageType: ProfileImageType
    ): BaseResponse<TokenResponse>
}