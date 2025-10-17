package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.Token
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    suspend fun validateToken(tokenType: String): Flow<Resource<String>>
    suspend fun reissueToken(): Flow<Resource<Token>>
}