package com.tico.pomorodo.ui.timer.setup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.common.util.Converters.Companion.TIME_ZONE
import com.tico.pomorodo.data.model.DailyTimerData
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.timer.CreateDailyTimerStatUseCase
import com.tico.pomorodo.domain.usecase.timer.GetDailyTimerDataUseCase
import com.tico.pomorodo.domain.usecase.timer.UpdateTargetFocusTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalTime
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import javax.inject.Inject

const val TAG = "TimerSetupViewModel"

@HiltViewModel
class TimerSetupViewModel @Inject constructor(
    private val createDailyTimerStatUseCase: CreateDailyTimerStatUseCase,
    private val getDailyTimerDataUseCase: GetDailyTimerDataUseCase,
    private val updateTargetFocusTimeUseCase: UpdateTargetFocusTimeUseCase,
) : ViewModel() {
    private val _dailyTimerData: MutableStateFlow<DailyTimerData?> = MutableStateFlow(null)
    val dailyTimerData: StateFlow<DailyTimerData?> = _dailyTimerData

    private val _concentrationTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0))
    val concentrationTime: StateFlow<Time> = _concentrationTime

    private val _breakTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0))
    val breakTime: StateFlow<Time> = _breakTime

    init {
        getTodayDailyTimerData()
    }

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second ?: 0)
    }

    fun setBreakTime(hour: Int, minute: Int) {
        _breakTime.value = Time(hour, minute)
    }

    private fun getTodayDailyTimerData() = viewModelScope.launch {
        val todayDateTime = Clock.System.todayIn(TIME_ZONE)

        getDailyTimerDataUseCase(statDate = todayDateTime).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _dailyTimerData.value = result.data
                    } else {
                        // dailyTimerData가 null이면 해당 날짜 데이터가 아직 생성되지 않았다는 뜻이므로 새로운 데이터를 생성해 room에 저장한다.
                        createTodayDailyTimerData()
                    }
                }

                is Resource.Failure.Error ->
                    Log.e(TAG, "getDailyTimerDate: ${result.message}")

                is Resource.Failure.Exception ->
                    Log.e(TAG, "getDailyTimerDate: ${result.code} ${result.message}")

                Resource.Loading -> {}
            }
        }
    }

    private fun createTodayDailyTimerData() = viewModelScope.launch {
        createDailyTimerStatUseCase(statDate = Clock.System.todayIn(TIME_ZONE))
    }

    fun updateConcentrationGoal(hour: Int, minute: Int, second: Int) = viewModelScope.launch {
        if (dailyTimerData.value != null) {
            val updatedDailyTimerData = dailyTimerData.value!!.copy(
                targetFocusTime = LocalTime(hour, minute, second),
                updatedAt = Clock.System.now().toLocalDateTime(TIME_ZONE)
            )
            _dailyTimerData.value = updatedDailyTimerData

            updateTargetFocusTimeUseCase(updatedDailyTimerData)
        } else {
            Log.e(TAG, "updateConcentrationGoal: dailyTimerData is null")
        }
    }

    fun initTimer() {
        _concentrationTime.value = Time(0, 0)
        _breakTime.value = Time(0, 0)
    }
}