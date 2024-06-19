package com.tico.pomorodo.ui.todo.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.ui.todo.view.Category
import com.tico.pomorodo.ui.todo.view.TodoData
import com.tico.pomorodo.ui.todo.view.TodoState
import com.tico.pomorodo.ui.todo.view.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TodoViewModel() : ViewModel() {
    private var _selectedProfileIndex = MutableStateFlow(-1)
    val selectedProfileIndex: StateFlow<Int>
        get() = _selectedProfileIndex.asStateFlow()

    private var _userList = MutableStateFlow(DataSource.userList)
    val userList: StateFlow<List<User>>
        get() = _userList.asStateFlow()

    private var _categoryList = MutableStateFlow(DataSource.todoList)
    val categoryList: StateFlow<List<Category>>
        get() = _categoryList.asStateFlow()

    fun setSelectedProfileIndex(index: Int) {
        _selectedProfileIndex.value = index
    }

    fun addNewTodoItem(categoryIndex: Int, inputText: String) {
        if (validateTodoInput(inputText)) {
            val newTodoData = TodoData(
                name = inputText,
                state = TodoState.UNCHECKED,
                completeGroupNumber = 0
            )
            val newList = categoryList.value.toMutableList()
            val newTodoList = newList[categoryIndex].todoList.toMutableList()
            newTodoList.add(0, newTodoData)
            newList[categoryIndex] = newList[categoryIndex].copy(todoList = newTodoList)
            _categoryList.value = newList
        }
    }

    fun changeTodoState(categoryIndex: Int, todoIndex: Int, state: TodoState) {
        val newState = when (state) {
            TodoState.UNCHECKED -> TodoState.CHECKED
            TodoState.CHECKED -> TodoState.GOING
            TodoState.GOING -> TodoState.UNCHECKED
        }
        val newList = categoryList.value.toMutableList()
        val newTodoList = newList[categoryIndex].todoList.toMutableList()
        val newItem = newTodoList[todoIndex].copy(state = newState)
        newTodoList[todoIndex] = newItem
        newList[categoryIndex] = newList[categoryIndex].copy(todoList = newTodoList)
        _categoryList.value = newList
    }

    private fun validateTodoInput(inputText: String): Boolean {
        return inputText.isNotBlank()
    }
}