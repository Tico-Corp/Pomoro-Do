package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import retrofit2.http.POST
import retrofit2.http.Path

interface FollowApiService {

    @POST("v1/users/{userId}/follow")
    suspend fun follow(@Path(value = "userId") userId: String): BaseResponse<String>
}