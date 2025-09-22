package com.tico.pomorodo.data.model

data class CategoryWithTodoItem(
    val categoryId: Int,
    val title: String,
    val type: CategoryType,
    val todoList: List<TodoData> = listOf(),
    val openSettings: OpenSettings = OpenSettings.PUBLIC,
    val groupMemberCount: Int = 0,
    val groupMember: List<User>? = null
)