package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.FollowResponse

interface FollowRemoteDataSource {

    suspend fun findUserByNickname(nickname: String): BaseResponse<List<FollowResponse>>
}