package com.tico.pomorodo.domain.usecase.timer

import com.tico.pomorodo.domain.repository.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetConcentrationGoalUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(userId: Int) = withContext(Dispatchers.IO) {
        timerRepository.getConcentrationGoal(userId)
    }
}