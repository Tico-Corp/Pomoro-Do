package com.tico.pomorodo.ui.timer.running.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BreakTimeViewModel @Inject constructor() : ViewModel() {
    private val _breakTime: MutableStateFlow<Time> = MutableStateFlow(Time())
    val breakTime: StateFlow<Time> = _breakTime

    private val _timerMaxValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val timerMaxValue: StateFlow<Int> = _timerMaxValue

    fun initialBreakTime(breakTime: Int) {
        _breakTime.value = Time(breakTime / 60, breakTime % 60, 0)
        _timerMaxValue.value = breakTime * 60
    }

    fun setBreakTime(hour: Int, minute: Int, second: Int? = null) {
        _breakTime.value = Time(hour, minute, second)
    }
}