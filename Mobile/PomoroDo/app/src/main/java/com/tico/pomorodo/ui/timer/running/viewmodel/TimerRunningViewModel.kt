package com.tico.pomorodo.ui.timer.running.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.model.Time
import com.tico.pomorodo.data.model.TodoData
import com.tico.pomorodo.data.model.TodoState
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

    private val _todoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(DataSource.todoList)
    private val _oldTodoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(_todoList.value)
    val todoList: StateFlow<List<TodoData>> = _todoList

    private val _isInitialized: MutableStateFlow<Boolean> = MutableStateFlow(false)

    fun initialConcentrationTime(concentrationTime: Int, breakTime: Int) {
        if (!_isInitialized.value) {
            _concentrationTime.value = Time(concentrationTime / 60, concentrationTime % 60, 0)
            _breakTime.value = Time(breakTime / 60, breakTime % 60, 0)
            _timerMaxValue.value = concentrationTime * 60
            _isInitialized.value = true
        }
    }

    fun initialBreakTime() {
        if (!_isInitialized.value) {
            _timerMaxValue.value = breakTime.value.hour * 60 * 60 + breakTime.value.minute * 60
            _isInitialized.value = true
        }
    }

    fun setConcentrationTime(hour: Int, minute: Int, second: Int? = null) {
        _concentrationTime.value = Time(hour, minute, second)
    }

    fun setBreakTime(hour: Int, minute: Int, second: Int? = null) {
        _breakTime.value = Time(hour, minute, second)
    }

    fun changeTodoState(todoIndex: Int, state: TodoState) {
        val newState = when (state) {
            TodoState.UNCHECKED -> TodoState.CHECKED
            TodoState.CHECKED -> TodoState.GOING
            TodoState.GOING -> TodoState.UNCHECKED
        }
        val newTodoList = _todoList.value.toMutableList()
        val newItem = newTodoList[todoIndex].copy(state = newState)
        newTodoList[todoIndex] = newItem
        _todoList.value = newTodoList
    }

    fun resetTodoState() {
        _todoList.value = _oldTodoList.value
    }

    fun setInitializedFlag() {
        _isInitialized.value = false
    }
}