package com.tico.pomorodo.data.model

data class AllCategory(
    val personalCategories: List<PersonalCategory>,
    val groupCategories: List<GroupCategory>,
    val categoryInvitations: List<CategoryInvitation>
)

data class PersonalCategory(
    val categoryId: Int,
    val categoryName: String
)

data class GroupCategory(
    val categoryId: Int,
    val categoryName: String,
    val totalMembers: Int
)

data class CategoryInvitation(
    val categoryInvitationId: Int,
    val categoryName: String,
    val ownerNickname: String
)