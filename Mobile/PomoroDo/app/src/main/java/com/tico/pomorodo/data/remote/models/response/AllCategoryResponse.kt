package com.tico.pomorodo.data.remote.models.response

import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.model.AllCategory
import com.tico.pomorodo.data.model.CategoryInvitation
import com.tico.pomorodo.data.model.CategoryList
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.SerialName
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
    @SerialName("totalMembers")
    val totalMemberCount: Int = 0
)

@Serializable
data class CategoryInvitationResponse(
    val categoryInvitationId: Int,
    val categoryName: String,
    val ownerNickname: String
)

@Serializable
data class DecideCategoryInvitationResponse(
    val categoryId: Int,
    val name: String,
    @SerialName("totalMembers")
    val totalMemberCount: Int = 0
)

@Serializable
data class InsertUpdateCategoryResponse(
    val categoryId: Int,
    val name: String,
    val type: CategoryType,
    val visibility: OpenSettings,
    @SerialName("totalMembers")
    val totalMemberCount: Int = 0
)

fun AllCategoryResponse.toAllCategory() = AllCategory(
    personalCategories = personalCategories.map { it.toCategoryList(CategoryType.PERSONAL) },
    groupCategories = groupCategories.map { it.toCategoryList(CategoryType.GROUP) },
    categoryInvitations = categoryInvitations.map { it.toCategoryInvitation() }
)

fun CategoryListResponse.toCategoryList(type: CategoryType) = CategoryList(
    categoryId = categoryId,
    categoryName = categoryName,
    totalMemberCount = totalMemberCount,
    type = type
)

fun CategoryInvitationResponse.toCategoryInvitation() = CategoryInvitation(
    categoryInvitationId = categoryInvitationId,
    categoryName = categoryName,
    ownerNickname = ownerNickname
)

fun DecideCategoryInvitationResponse.toCategoryEntity(
    type: CategoryType,
    openSettings: OpenSettings
) = CategoryEntity(
    id = categoryId,
    title = name,
    type = type,
    totalMemberCount = totalMemberCount,
    openSettings = openSettings
)

fun InsertUpdateCategoryResponse.toCategoryEntity() = CategoryEntity(
    id = categoryId,
    title = name,
    type = type,
    totalMemberCount = totalMemberCount,
    openSettings = visibility
)