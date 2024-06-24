package com.tico.pomorodo.data.local.entity

data class Category(
    val id: String,
    val title: String,
    val todoList: List<TodoData>,
    val groupNumber: Int = 0
)