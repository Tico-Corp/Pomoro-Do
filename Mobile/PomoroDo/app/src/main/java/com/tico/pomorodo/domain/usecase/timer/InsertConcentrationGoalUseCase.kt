package com.tico.pomorodo.domain.usecase.timer

import com.tico.pomorodo.data.model.DailyTimerData
import com.tico.pomorodo.domain.repository.TimerRepository
import javax.inject.Inject

class InsertConcentrationGoalUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(dailyTimerData: DailyTimerData) {
        timerRepository.insertConcentrationGoal(dailyTimerData)
    }
}