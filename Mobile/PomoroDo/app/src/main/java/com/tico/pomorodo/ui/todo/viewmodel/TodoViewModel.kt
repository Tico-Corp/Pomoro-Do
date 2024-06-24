package com.tico.pomorodo.ui.todo.viewmodel

import androidx.lifecycle.ViewModel
import com.tico.pomorodo.data.local.datasource.DataSource
import com.tico.pomorodo.data.local.entity.Category
import com.tico.pomorodo.data.local.entity.InviteCategory
import com.tico.pomorodo.data.local.entity.TodoData
import com.tico.pomorodo.data.local.entity.User
import com.tico.pomorodo.ui.todo.view.TodoState
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

    private var _inviteGroupCategoryList = MutableStateFlow(DataSource.inviteList)
    val inviteGroupCategoryList: StateFlow<List<InviteCategory>>
        get() = _inviteGroupCategoryList.asStateFlow()

    private var _todoMakeVisible = MutableStateFlow(false)
    val todoMakeVisible: StateFlow<Boolean>
        get() = _todoMakeVisible

    private var _selectedCategoryIndex = MutableStateFlow(-1)
    val selectedCategoryIndex: StateFlow<Int>
        get() = _selectedCategoryIndex

    private var _inputText = MutableStateFlow("")
    val inputText: StateFlow<String>
        get() = _inputText

    fun setSelectedProfileIndex(index: Int) {
        _selectedProfileIndex.value = index
    }

    fun setSelectedCategoryIndex(index: Int) {
        _selectedCategoryIndex.value = index
    }

    fun setTodoMakeVisible(value: Boolean) {
        _todoMakeVisible.value = value
    }

    fun setInputText(text: String) {
        _inputText.value = text
    }

    fun addNewTodoItem() {
        _inputText.value = _inputText.value.trim()
        if (validateTodoInput(inputText.value)) {
            val newTodoData = TodoData(
                id = "4",
                name = inputText.value,
                state = TodoState.UNCHECKED,
                completeGroupNumber = 0
            )
            val newList = categoryList.value.toMutableList()
            val newTodoList = newList[selectedCategoryIndex.value].todoList.toMutableList()
            newTodoList.add(0, newTodoData)
            newList[selectedCategoryIndex.value] =
                newList[selectedCategoryIndex.value].copy(todoList = newTodoList)
            _categoryList.value = newList
        }
        _todoMakeVisible.value = false
        _selectedCategoryIndex.value = -1
        _inputText.value = ""
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