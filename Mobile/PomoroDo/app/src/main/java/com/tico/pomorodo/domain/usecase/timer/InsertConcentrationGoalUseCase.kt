package com.tico.pomorodo.domain.usecase.timer

import com.tico.pomorodo.data.model.TimerSettingData
import com.tico.pomorodo.domain.repository.TimerRepository
import javax.inject.Inject

class InsertConcentrationGoalUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(timerSettingData: TimerSettingData) {
        timerRepository.insertConcentrationGoal(timerSettingData)
    }
}