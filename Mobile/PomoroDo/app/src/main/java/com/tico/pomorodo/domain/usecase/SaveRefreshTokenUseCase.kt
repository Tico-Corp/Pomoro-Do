package com.tico.pomorodo.domain.usecase

import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveRefreshTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(token: String) =
        withContext(Dispatchers.IO) { authRepository.saveRefreshToken(token) }
}