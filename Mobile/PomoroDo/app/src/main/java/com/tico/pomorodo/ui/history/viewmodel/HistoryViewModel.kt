package com.tico.pomorodo.ui.history.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.model.TimeLineData
import com.tico.pomorodo.data.model.TodoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class HistoryViewModel @Inject constructor() : ViewModel() {
    private var _timeLine = MutableStateFlow<List<TimeLineData>>(emptyList())
    val timeLine: StateFlow<List<TimeLineData>>
        get() = _timeLine.asStateFlow()

    private var _todoList = MutableStateFlow<List<TodoData>>(emptyList())
    val todoList: StateFlow<List<TodoData>>
        get() = _todoList.asStateFlow()

    private var dialogSelectedIndex = MutableStateFlow(-1)

    fun setTodoEdit(newTodoList: List<TodoData>) {
        _todoList.value = newTodoList
    }

    fun setDialogSelectedIndex(index: Int) {
        dialogSelectedIndex.value = index
    }
}