package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.user.UserInfoResponse
import com.tico.pomorodo.data.remote.service.UserApiService
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(private val userApiService: UserApiService) :
    UserRemoteDataSource {
    override suspend fun getUserInfo(): BaseResponse<UserInfoResponse> = userApiService.getUserInfo()
}