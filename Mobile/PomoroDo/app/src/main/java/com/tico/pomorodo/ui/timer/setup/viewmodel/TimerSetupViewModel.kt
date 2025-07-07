package com.tico.pomorodo.ui.timer.setup.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.local.datasource.DataSource.INITIAL_TIMER_SETTING_DATA
import com.tico.pomorodo.data.model.DailyTimerData
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.usecase.timer.GetConcentrationGoalUseCase
import com.tico.pomorodo.domain.usecase.timer.InsertConcentrationGoalUseCase
import com.tico.pomorodo.domain.usecase.timer.UpdateTargetFocusTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import javax.inject.Inject

const val TEMP_USER_ID = 2000
const val TAG = "TimerSetupViewModel"

@HiltViewModel
class TimerSetupViewModel @Inject constructor(
    private val insertConcentrationGoalUseCase: InsertConcentrationGoalUseCase,
    private val getConcentrationGoalUseCase: GetConcentrationGoalUseCase,
    private val updateTargetFocusTimeUseCase: UpdateTargetFocusTimeUseCase,
) : ViewModel() {
    private val _dailyTimerData: MutableStateFlow<DailyTimerData?> = MutableStateFlow(null)
    val dailyTimerData: StateFlow<DailyTimerData?> = _dailyTimerData

    private val _concentrationTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0))
    val concentrationTime: StateFlow<Time> = _concentrationTime

    private val _breakTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0))
    val breakTime: StateFlow<Time> = _breakTime

    private val _concentrationGoal: MutableStateFlow<LocalTime> =
        MutableStateFlow(LocalTime(0, 0, 0))
    val concentrationGoal: StateFlow<LocalTime> = _concentrationGoal

    private val _totalConcentrationTime: MutableStateFlow<Time> =
        MutableStateFlow(Time(0, 0, 0))
    val totalConcentrationTime: StateFlow<Time> = _totalConcentrationTime

    private val _totalBreakTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0, 0))
    val totalBreakTime: StateFlow<Time> = _totalBreakTime

    init {
        viewModelScope.launch {
            getConcentrationGoalUseCase(TEMP_USER_ID).collect { result ->

                if (result is Resource.Success && result.data == null) {
                    insertConcentrationGoal(INITIAL_TIMER_SETTING_DATA)
                }
            }
        }

        getConcentrationGoal()
    }

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second ?: 0)
    }

    fun setBreakTime(hour: Int, minute: Int) {
        _breakTime.value = Time(hour, minute)
    }

    private fun insertConcentrationGoal(dailyTimerData: DailyTimerData) =
        viewModelScope.launch {
            insertConcentrationGoalUseCase(dailyTimerData)
        }

    private fun getConcentrationGoal(userId: Int = TEMP_USER_ID) = viewModelScope.launch {
        getConcentrationGoalUseCase(userId).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (result.data != null) {
                        _dailyTimerData.value = result.data
                        _concentrationGoal.value = result.data.targetFocusTime
                    } else {
                        Log.e(TAG, "getConcentrationGoal: No data found")
                    }
                }

                is Resource.Failure.Error -> {
                    Log.e(TAG, "getConcentrationGoal: ${result.message}")
                }

                is Resource.Failure.Exception -> {
                    Log.e(TAG, "getConcentrationGoal: ${result.code} ${result.message}")
                }

                Resource.Loading -> {}
            }

        }
    }

    fun updateConcentrationGoal(hour: Int, minute: Int, second: Int) = viewModelScope.launch {
        if (dailyTimerData.value != null) {
            _concentrationGoal.value = LocalTime(hour, minute, second)
            val updatedDailyTimerData = dailyTimerData.value!!.copy(
                targetFocusTime = LocalTime(hour, minute, second),
                updatedAt = System.currentTimeMillis()
            )
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