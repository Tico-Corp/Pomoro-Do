package com.tico.pomorodo.ui.timer.setup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.data.model.TimerSettingData
import com.tico.pomorodo.domain.usecase.timer.GetConcentrationGoalUseCase
import com.tico.pomorodo.domain.usecase.timer.UpdateTargetTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime
import javax.inject.Inject

@HiltViewModel
class TimerSetupViewModel @Inject constructor(
    private val getConcentrationGoalUseCase: GetConcentrationGoalUseCase,
    private val updateTargetTimeUseCase: UpdateTargetTimeUseCase,
) : ViewModel() {
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

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second ?: 0)
    }

    fun setBreakTime(hour: Int, minute: Int) {
        _breakTime.value = Time(hour, minute)
    }

    fun getConcentrationGoal(userId: Int) = viewModelScope.launch {
        getConcentrationGoalUseCase(userId)
    }

    fun updateConcentrationGoal(hour: Int, minute: Int, second: Int) = viewModelScope.launch {
        _concentrationGoal.value = LocalTime(hour, minute, second)
        val timerSettingData =
            TimerSettingData(
                0,
                1000,
                LocalTime(hour, minute, second),
                System.currentTimeMillis()
            )
        updateTargetTimeUseCase(timerSettingData)
    }

    fun initTimer() {
        _concentrationTime.value = Time(0, 0)
        _breakTime.value = Time(0, 0)
    }
}