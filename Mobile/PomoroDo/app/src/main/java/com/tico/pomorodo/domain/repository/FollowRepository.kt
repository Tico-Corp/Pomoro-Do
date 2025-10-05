package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface FollowRepository {

    suspend fun findUserByNickname(nickname: String): Flow<Resource<List<Follow>>>
}