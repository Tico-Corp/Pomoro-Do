package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.CategoryEntity

data class Category(
    val id: Int = 0,
    val title: String,
    val type: CategoryType,
    val openSettings: OpenSettings = OpenSettings.PUBLIC,
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
    openSettings = openSettings,
    groupReader = groupReader,
    isGroupReader = isGroupReader,
    groupMemberCount = groupMemberCount,
    groupMember = groupMember?.map(User::toUserEntity)
)