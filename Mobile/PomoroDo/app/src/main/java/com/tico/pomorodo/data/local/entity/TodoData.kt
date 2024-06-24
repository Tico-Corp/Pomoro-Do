package com.tico.pomorodo.data.local.entity

import com.tico.pomorodo.ui.todo.view.TodoState

data class TodoData(
    val id: String,
    val name: String,
    var state: TodoState,
    val completeGroupNumber: Int? = null,
    val likedNumber: Int = 0,
)