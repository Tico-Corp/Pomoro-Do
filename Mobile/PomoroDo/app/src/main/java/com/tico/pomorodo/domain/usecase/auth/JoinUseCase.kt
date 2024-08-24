package com.tico.pomorodo.domain.usecase.auth

import com.tico.pomorodo.domain.model.ProfileImageType
import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class JoinUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        name: String,
        profile: File?,
        profileImageType: ProfileImageType
    ) = withContext(Dispatchers.IO) {
            authRepository.requestJoin(
                name,
                profile,
                profileImageType
            )
        }
}