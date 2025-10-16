package com.tico.pomorodo.data.remote.models.response.category

import com.tico.pomorodo.data.model.AllCategory
import com.tico.pomorodo.data.model.CategoryType
import kotlinx.serialization.Serializable

@Serializable
data class AllCategoryResponse(
    val personalCategories: List<CategoryListResponse>,
    val groupCategories: List<CategoryListResponse>,
    val categoryInvitations: List<CategoryInvitationResponse>
)

fun AllCategoryResponse.toAllCategory() = AllCategory(
    personalCategories = personalCategories.map { it.toCategoryList(CategoryType.PERSONAL) },
    groupCategories = groupCategories.map { it.toCategoryList(CategoryType.GROUP) },
    categoryInvitations = categoryInvitations.map { it.toCategoryInvitation() }
)