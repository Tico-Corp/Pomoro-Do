package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.CategoryEntity

data class Category(
    val id: Int = 0,
    val title: String,
    val type: CategoryType,
    val openSettings: OpenSettings = OpenSettings.PUBLIC,
    val ownerNickname: String? = null,
    val ownerFlag: Boolean,
    val groupMembers: List<User>? = null,
    val totalMembers: Int
)

enum class CategoryType {
    PERSONAL, GROUP
}

fun Category.toCategoryEntity() = CategoryEntity(
    id = id,
    title = title,
    type = type,
    openSettings = openSettings,
    ownerNickname = ownerNickname,
    ownerFlag = ownerFlag,
    groupMember = groupMembers?.map(User::toUserEntity),
    totalMembers = totalMembers
)