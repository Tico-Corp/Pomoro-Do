package com.tico.pomorodo.domain.usecase

import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetIdTokenUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(): String? =
        withContext(Dispatchers.IO) { authRepository.getIdToken() }
}