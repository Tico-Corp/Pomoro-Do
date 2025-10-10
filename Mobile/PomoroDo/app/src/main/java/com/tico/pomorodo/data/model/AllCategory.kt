package com.tico.pomorodo.data.model

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