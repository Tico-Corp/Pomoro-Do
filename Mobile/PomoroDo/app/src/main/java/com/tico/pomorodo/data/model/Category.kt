package com.tico.pomorodo.data.model

data class Category(
    val id: String,
    val title: String,
    val todoList: List<TodoData>,
    val groupNumber: Int = 0
)