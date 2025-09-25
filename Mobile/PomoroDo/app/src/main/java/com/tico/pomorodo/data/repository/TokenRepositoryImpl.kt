package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.model.Token
import com.tico.pomorodo.data.remote.datasource.TokenDataSource
import com.tico.pomorodo.data.remote.models.response.toToken
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource
) : TokenRepository {
    override suspend fun validateToken(tokenType: String): Flow<Resource<String>> = flow {
        emit(Resource.Loading)
        val data = wrapToResource(Dispatchers.IO) {
            tokenDataSource.validateToken(tokenType).data
        }
        emit(data)
    }.flowOn(Dispatchers.IO)

    override suspend fun reissueToken(): Flow<Resource<Token>> = flow {
        emit(Resource.Loading)
        val data = wrapToResource(Dispatchers.IO) {
            tokenDataSource.reissueToken().data.toToken()
        }
        emit(data)
    }.flowOn(Dispatchers.IO)
}