package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.TokenResponse
import com.tico.pomorodo.data.remote.service.ReissueTokenService
import com.tico.pomorodo.data.remote.service.TokenApiService
import javax.inject.Inject

class TokenDataSourceImpl @Inject constructor(
    private val tokenApiService: TokenApiService,
    private val reissueTokenService: ReissueTokenService,
) : TokenDataSource {

    override suspend fun validateToken(tokenType: String): BaseResponse<String> =
        tokenApiService.validateToken(tokenType)

    override suspend fun reissueToken(): BaseResponse<TokenResponse> =
        reissueTokenService.reissueToken()
}