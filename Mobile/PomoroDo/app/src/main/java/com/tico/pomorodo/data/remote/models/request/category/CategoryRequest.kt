package com.tico.pomorodo.data.remote.models.request.category

import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequest(
    val startDate: String,
    @SerialName("name")
    val title: String,
    val type: CategoryType,
    @SerialName("visibility")
    val openSettings: OpenSettings,
    val memberIds: List<Int>?
)