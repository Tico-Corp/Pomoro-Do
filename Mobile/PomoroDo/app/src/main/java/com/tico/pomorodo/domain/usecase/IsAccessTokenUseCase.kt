package com.tico.pomorodo.domain.usecase

import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsAccessTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Boolean =
        withContext(Dispatchers.IO) { authRepository.isAccessToken() }
}