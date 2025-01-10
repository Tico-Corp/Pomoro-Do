package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.CategoryEntity

data class Category(
    val id: Int,
    val title: String,
    val type: CategoryType,
    val todoList: List<TodoData>? = null,
    val openSettings: OpenSettings = OpenSettings.FULL,
    val groupReader: String? = null,
    val isGroupReader: Boolean? = null,
    val groupMemberCount: Int = 0,
    val groupMember: List<User>? = null
)

enum class CategoryType {
    GENERAL, GROUP
}

fun Category.toCategoryEntity() = CategoryEntity(
    id = id,
    title = title,
    type = type,
    openSettings = openSettings
)
