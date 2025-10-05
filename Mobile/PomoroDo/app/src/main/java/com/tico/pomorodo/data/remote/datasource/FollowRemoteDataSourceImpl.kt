package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.FollowResponse
import com.tico.pomorodo.data.remote.service.FollowApiService
import javax.inject.Inject

class FollowRemoteDataSourceImpl @Inject constructor(private val followApiService: FollowApiService) :
    FollowRemoteDataSource {
    override suspend fun findUserByNickname(nickname: String): BaseResponse<List<FollowResponse>> =
        followApiService.findUserByNickname(nickname)
}