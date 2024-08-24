package com.tico.pomorodo.domain.usecase.auth

import com.tico.pomorodo.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ValidateTokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(tokenType: String) =
        withContext(Dispatchers.IO) { tokenRepository.validateToken(tokenType) }
}