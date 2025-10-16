package com.tico.pomorodo.data.remote.models.request.category

import com.tico.pomorodo.data.model.DeletionOption
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDeleteRequest(
    val deletionOption: DeletionOption
)