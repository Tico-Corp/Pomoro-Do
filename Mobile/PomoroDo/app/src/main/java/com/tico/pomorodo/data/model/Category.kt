package com.tico.pomorodo.data.model

data class Category(
    val id: String,
    val title: String,
    val type: CategoryType,
    val todoList: List<TodoData>? = null,
    val openSettings: OpenSettings = OpenSettings.FULL,
    val groupNumber: Int = 0,
    val groupReader: String? = null,
    val isGroupReader: Boolean? = null,
    val groupMember: List<User>? = null
)

enum class CategoryType {
    NORMAL, GROUP
}