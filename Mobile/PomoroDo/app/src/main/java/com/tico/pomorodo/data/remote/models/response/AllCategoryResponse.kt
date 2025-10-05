package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.model.AllCategory
import com.tico.pomorodo.data.model.CategoryInvitation
import com.tico.pomorodo.data.model.CategoryList
import kotlinx.serialization.Serializable

@Serializable
data class AllCategoryResponse(
    val personalCategories: List<CategoryListResponse>,
    val groupCategories: List<CategoryListResponse>,
    val categoryInvitations: List<CategoryInvitationResponse>
)

@Serializable
data class CategoryListResponse(
    val categoryId: Int,
    val categoryName: String,
    val totalMembers: Int = 0
)

@Serializable
data class CategoryInvitationResponse(
    val categoryInvitationId: Int,
    val categoryName: String,
    val ownerNickname: String
)

fun AllCategoryResponse.toAllCategory() = AllCategory(
    personalCategories = personalCategories.map { it.toCategoryList() },
    groupCategories = groupCategories.map { it.toCategoryList() },
    categoryInvitations = categoryInvitations.map { it.toCategoryInvitation() }
)

fun CategoryListResponse.toCategoryList() = CategoryList(
    categoryId = categoryId,
    categoryName = categoryName,
    totalMembers = totalMembers
)

fun CategoryInvitationResponse.toCategoryInvitation() = CategoryInvitation(
    categoryInvitationId = categoryInvitationId,
    categoryName = categoryName,
    ownerNickname = ownerNickname
)