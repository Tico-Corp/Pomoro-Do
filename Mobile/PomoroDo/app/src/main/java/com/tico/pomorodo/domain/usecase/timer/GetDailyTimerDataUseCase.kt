package com.tico.pomorodo.domain.usecase.timer

import com.tico.pomorodo.domain.repository.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class GetDailyTimerDataUseCase @Inject constructor(private val timerRepository: TimerRepository) {
    suspend operator fun invoke(statDate: LocalDate) = withContext(Dispatchers.IO) {
        timerRepository.getDailyTimerData(statDate)
    }
}