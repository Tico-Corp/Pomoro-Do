package com.tico.pomorodo.domain.usecase.timer

import com.tico.pomorodo.domain.repository.TimerRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class CreateDailyTimerStatUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(statDate: LocalDate) {
        timerRepository.createDailyTimerStat(statDate)
    }
}