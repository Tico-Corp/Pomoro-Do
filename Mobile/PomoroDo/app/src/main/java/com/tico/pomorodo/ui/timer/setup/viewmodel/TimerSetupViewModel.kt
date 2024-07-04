package com.tico.pomorodo.ui.timer.setup.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TimerSetupViewModel @Inject constructor() : ViewModel() {
    private val _concentrationTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0))
    val concentrationTime: StateFlow<Time> = _concentrationTime

    private val _breakTime: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0))
    val breakTime: StateFlow<Time> = _breakTime

    private val _concentrationGoal: MutableStateFlow<Time> = MutableStateFlow(Time(0, 0, 0))
    val concentrationGoal: StateFlow<Time> = _concentrationGoal

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second)
    }

    fun setBreakTime(hour: Int, minute: Int) {
        _breakTime.value = Time(hour, minute)
    }

    fun setConcentrationGoal(hour: Int, minute: Int, second: Int) {
        _concentrationGoal.value = Time(hour, minute, second)
    }

    fun initTimer() {
        _concentrationTime.value = Time(0, 0)
        _breakTime.value = Time(0, 0)
    }
}