package com.tico.pomorodo.data.model

import com.tico.pomorodo.data.local.entity.CategoryEntity

data class AllCategory(
    val personalCategories: List<CategoryList>,
    val groupCategories: List<CategoryList>,
    val categoryInvitations: List<CategoryInvitation> = emptyList()
)

data class CategoryList(
    val categoryId: Int,
    val categoryName: String,
    val type: CategoryType,
    val totalMembers: Int
)

data class CategoryInvitation(
    val categoryInvitationId: Int,
    val categoryName: String,
    val ownerNickname: String
)

fun AllCategory.toCategoryEntity(): List<CategoryEntity> =
    personalCategories.map(CategoryList::toCategoryEntity) + groupCategories.map(CategoryList::toCategoryEntity)

fun CategoryList.toCategoryEntity() = CategoryEntity(
    id = categoryId,
    title = categoryName,
    type = type,
    totalMembers = totalMembers,
    openSettings = when (type) {
        CategoryType.PERSONAL -> OpenSettings.PUBLIC
        CategoryType.GROUP -> OpenSettings.GROUP
    }
)