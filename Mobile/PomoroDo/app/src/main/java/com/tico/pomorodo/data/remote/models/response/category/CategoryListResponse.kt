package com.tico.pomorodo.data.remote.models.response.category

import com.tico.pomorodo.data.model.CategoryList
import com.tico.pomorodo.data.model.CategoryType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryListResponse(
    val categoryId: Int,
    val categoryName: String,
    @SerialName("totalMembers")
    val totalMemberCount: Int = 0
)

fun CategoryListResponse.toCategoryList(type: CategoryType) = CategoryList(
    categoryId = categoryId,
    categoryName = categoryName,
    totalMemberCount = totalMemberCount,
    type = type
)