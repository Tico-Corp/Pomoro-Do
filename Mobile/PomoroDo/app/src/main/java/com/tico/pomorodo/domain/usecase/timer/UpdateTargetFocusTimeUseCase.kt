package com.tico.pomorodo.domain.usecase.timer

import com.tico.pomorodo.data.model.DailyTimerData
import com.tico.pomorodo.domain.repository.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateTargetFocusTimeUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(dailyTimerData: DailyTimerData) =
        withContext(Dispatchers.IO) {
            timerRepository.updateTargetFocusTime(dailyTimerData)
        }
}