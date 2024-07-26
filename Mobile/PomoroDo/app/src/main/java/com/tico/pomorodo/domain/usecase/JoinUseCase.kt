package com.tico.pomorodo.domain.usecase

import com.tico.pomorodo.data.remote.models.request.UserInfoRequestBody
import com.tico.pomorodo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class JoinUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(member: UserInfoRequestBody, image: File? = null) =
        withContext(Dispatchers.IO) { authRepository.requestJoin(member, image) }
}