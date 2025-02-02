package com.tico.pomorodo.ui.timer.running.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.data.model.TodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ConcentrationTimeViewModel @Inject constructor() : ViewModel() {
    private val _concentrationTime: MutableStateFlow<Time> = MutableStateFlow(Time())
    val concentrationTime: StateFlow<Time> = _concentrationTime

    private val _timerMaxValue: MutableStateFlow<Int> = MutableStateFlow(0)
    val timerMaxValue: StateFlow<Int> = _timerMaxValue

    private val _todoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(emptyList())
    val todoList: StateFlow<List<TodoData>> = _todoList

    fun initialConcentrationTime(concentrationTime: Int) {
        _concentrationTime.value = Time(concentrationTime / 60, concentrationTime % 60, 0)
        _timerMaxValue.value = concentrationTime * 60
    }

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second)
    }

    fun setTodoList(newTodoList: List<TodoData>) {
        _todoList.value = newTodoList
    }
}