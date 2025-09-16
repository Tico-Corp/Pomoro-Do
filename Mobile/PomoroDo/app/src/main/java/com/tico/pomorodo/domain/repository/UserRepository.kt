package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.UserProfile
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserProfile(): Flow<Resource<UserProfile>>
    suspend fun getMyUserId(): Flow<Resource<Int>>
}