package com.tico.pomorodo.ui.timer.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class Time(
    var hour: Int = 0,
    var minute: Int = 0,
    var second: Int? = null
)

@HiltViewModel
class TimerViewModel @Inject constructor() : ViewModel() {
    private val _concentrationTime: MutableStateFlow<Time> = MutableStateFlow(Time())
    val concentrationTime: StateFlow<Time> = _concentrationTime

    private val _breakTime: MutableStateFlow<Time> = MutableStateFlow(Time())
    val breakTime: StateFlow<Time> = _breakTime

    fun setConcentrationTime(hour: Int, minute: Int) {
        _concentrationTime.value = Time(hour, minute)
    }

    fun setConcentrationTime(hour: Int, minute: Int, second: Int) {
        _concentrationTime.value = Time(hour, minute, second)
    }

    fun setBreakTime(hour: Int, minute: Int) {
        _breakTime.value = Time(hour, minute)
    }

    fun setBreakTime(hour: Int, minute: Int, second: Int) {
        _breakTime.value = Time(hour, minute, second)
    }
}