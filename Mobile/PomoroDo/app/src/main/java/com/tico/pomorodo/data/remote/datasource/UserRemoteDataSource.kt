package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.user.UserInfoResponse

interface UserRemoteDataSource {
    suspend fun getUserInfo(): BaseResponse<UserInfoResponse>
}