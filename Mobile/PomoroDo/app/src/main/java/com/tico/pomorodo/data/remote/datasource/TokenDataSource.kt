package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.user.TokenResponse

interface TokenDataSource {
    suspend fun validateToken(tokenType: String): BaseResponse<String>
    suspend fun reissueToken(): BaseResponse<TokenResponse>
}