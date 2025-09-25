package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.AllCategory
import com.tico.pomorodo.data.model.CategoryInvitation
import com.tico.pomorodo.data.model.GroupCategory
import com.tico.pomorodo.data.model.PersonalCategory
import kotlinx.serialization.Serializable

@Serializable
data class AllCategoryResponse(
    val personalCategories: List<PersonalCategoryResponse>,
    val groupCategories: List<GroupCategoryResponse>,
    val categoryInvitations: List<CategoryInvitationResponse>
)

@Serializable
data class PersonalCategoryResponse(
    val categoryId: Int,
    val categoryName: String
)

@Serializable
data class GroupCategoryResponse(
    val categoryId: Int,
    val categoryName: String,
    val totalMembers: Int
)

@Serializable
data class CategoryInvitationResponse(
    val categoryInvitationId: Int,
    val categoryName: String,
    val ownerNickname: String
)

fun AllCategoryResponse.toAllCategory() = AllCategory(
    personalCategories = personalCategories.map { it.toPersonalCategory() },
    groupCategories = groupCategories.map { it.toGroupCategory() },
    categoryInvitations = categoryInvitations.map { it.toCategoryInvitation() }
)

fun PersonalCategoryResponse.toPersonalCategory() = PersonalCategory(
    categoryId = categoryId,
    categoryName = categoryName
)

fun GroupCategoryResponse.toGroupCategory() = GroupCategory(
    categoryId = categoryId,
    categoryName = categoryName,
    totalMembers = totalMembers
)

fun CategoryInvitationResponse.toCategoryInvitation() = CategoryInvitation(
    categoryInvitationId = categoryInvitationId,
    categoryName = categoryName,
    ownerNickname = ownerNickname
)