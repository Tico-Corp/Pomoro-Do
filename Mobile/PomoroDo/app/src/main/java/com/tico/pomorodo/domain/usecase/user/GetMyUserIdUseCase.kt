package com.tico.pomorodo.domain.usecase.user

import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMyUserIdUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Flow<Resource<Int>> = withContext(Dispatchers.IO) {
        userRepository.getMyUserId()
    }
}