package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.remote.datasource.FollowRemoteDataSource
import com.tico.pomorodo.data.remote.models.response.toFollow
import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.FollowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FollowRepositoryImpl @Inject constructor(private val followRemoteDataSource: FollowRemoteDataSource) :
    FollowRepository {

    override suspend fun findUserByNickname(nickname: String): Flow<Resource<List<Follow>>> = flow {
        emit(Resource.Loading)

        val result = wrapToResource(Dispatchers.IO) {
            followRemoteDataSource.findUserByNickname(nickname).data.map { it.toFollow() }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}