package com.tico.pomorodo.domain.usecase.follow

import com.tico.pomorodo.domain.model.Follow
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.FollowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindUserByNicknameUseCase @Inject constructor(private val followRepository: FollowRepository) {

    suspend operator fun invoke(nickname: String): Flow<Resource<List<Follow>>> =
        withContext(Dispatchers.IO) {
            followRepository.findUserByNickname(nickname)
        }
}