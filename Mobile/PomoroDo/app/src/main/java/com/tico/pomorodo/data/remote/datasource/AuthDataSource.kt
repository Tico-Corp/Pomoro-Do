package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.user.TokenResponse
import com.tico.pomorodo.domain.model.ProfileImageType
import java.io.File

interface AuthDataSource {
    suspend fun requestLogin(): BaseResponse<TokenResponse>

    suspend fun requestJoin(
        name: String,
        profile: File?,
        profileImageType: ProfileImageType
    ): BaseResponse<TokenResponse>
}