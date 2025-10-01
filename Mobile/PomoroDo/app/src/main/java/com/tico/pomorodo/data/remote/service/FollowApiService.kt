package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.FollowResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface FollowApiService {

    @GET("follow/find/{nickname}")
    suspend fun findUserByNickname(@Path("nickname") nickname: String): BaseResponse<List<FollowResponse>>
}