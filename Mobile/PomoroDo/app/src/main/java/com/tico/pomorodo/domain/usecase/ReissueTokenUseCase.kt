package com.tico.pomorodo.domain.usecase

import com.tico.pomorodo.domain.repository.TokenRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReissueTokenUseCase @Inject constructor(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke() =
        withContext(Dispatchers.IO) { tokenRepository.reissueToken() }
}