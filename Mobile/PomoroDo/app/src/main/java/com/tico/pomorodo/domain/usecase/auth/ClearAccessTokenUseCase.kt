package com.tico.pomorodo.domain.usecase.auth

import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ClearAccessTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() =
        withContext(Dispatchers.IO) { authRepository.clearAccessToken() }
}