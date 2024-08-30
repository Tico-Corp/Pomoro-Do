package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.TokenResponse
import retrofit2.http.POST

interface ReissueTokenService {
    @POST("/api/v1/auth/tokens/reissue")
    suspend fun reissueToken(): BaseResponse<TokenResponse>
}