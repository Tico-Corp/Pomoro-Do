package com.tico.pomorodo.ui.timer.running.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TimerRunningViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) :
    ViewModel() {
    private val _concentrationTime: MutableStateFlow<Time> = MutableStateFlow(Time())
    val concentrationTime: StateFlow<Time> = _concentrationTime

    private val _breakTime: MutableStateFlow<Time> = MutableStateFlow(Time())
    val breakTime: StateFlow<Time> = _breakTime

    private val _timerMaxValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val timerMaxValue: StateFlow<Int> = _timerMaxValue

    fun initialConcentrationTime(time: Int) {
        _concentrationTime.value = Time(time / 60, time % 60, 0)
        _timerMaxValue.value = time * 60
    }

    fun initialBreakTime(time: Int) {
        _concentrationTime.value = Time(time / 60, time % 60, 0)
        _timerMaxValue.value = time * 60
    }

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second)
    }

    fun setBreakTime(hour: Int, minute: Int) {
        _breakTime.value = Time(hour, minute)
    }
}