package com.tico.pomorodo.domain.usecase.auth

import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveFIDUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(fid: String) =
        withContext(Dispatchers.IO) { authRepository.saveFID(fid) }
}