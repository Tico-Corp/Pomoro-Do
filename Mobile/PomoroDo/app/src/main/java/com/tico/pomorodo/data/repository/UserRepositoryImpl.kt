package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.model.UserProfile
import com.tico.pomorodo.data.remote.datasource.UserRemoteDataSource
import com.tico.pomorodo.data.remote.models.response.toUserProfile
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) :
    UserRepository {
    override suspend fun getUserProfile(): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading)

        val result = userRemoteDataSource.getUserInfo()
            .let { wrapToResource(Dispatchers.IO) { it.data.toUserProfile() } }
        emit(result)
    }.flowOn(Dispatchers.IO)
}