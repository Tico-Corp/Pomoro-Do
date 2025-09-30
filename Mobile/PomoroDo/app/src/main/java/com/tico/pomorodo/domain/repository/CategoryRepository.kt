package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.AllCategory
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.Decision
import com.tico.pomorodo.data.model.DeletionOption
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategory(): Flow<Resource<AllCategory>>

    suspend fun getCategoryInfo(categoryId: Int): Flow<Resource<Category>>

    suspend fun insertCategory(
        title: String,
        type: CategoryType,
        openSettings: OpenSettings,
        groupMemberIds: List<Int>?,
        startDate: String
    )

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryId: Int, deleteOption: DeletionOption)

}