package com.tico.pomorodo.data.remote.models.request.category

import com.tico.pomorodo.data.model.OpenSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryUpdateRequest(
    @SerialName("name")
    val title: String,
    @SerialName("visibility")
    val openSettings: OpenSettings
)