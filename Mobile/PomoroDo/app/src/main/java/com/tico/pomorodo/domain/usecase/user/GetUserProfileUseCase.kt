package com.tico.pomorodo.domain.usecase.user

import com.tico.pomorodo.data.model.UserProfile
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(): Flow<Resource<UserProfile>> = withContext(Dispatchers.IO) {
        userRepository.getUserProfile()
    }
}