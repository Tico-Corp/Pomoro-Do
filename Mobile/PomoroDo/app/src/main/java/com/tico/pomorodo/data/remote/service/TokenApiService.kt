package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface TokenApiService {
    @POST("/api/v1/auth/tokens/validation")
    suspend fun validateToken(@Query("tokenType") tokenType: String): BaseResponse<String>
}