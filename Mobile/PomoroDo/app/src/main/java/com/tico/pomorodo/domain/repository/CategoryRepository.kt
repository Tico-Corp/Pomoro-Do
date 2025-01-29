package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategory(): Flow<Resource<List<Category>>>

    suspend fun getCategoryInfo(categoryId: Int): Flow<Resource<Category>>

    suspend fun insert(
        title: String,
        type: CategoryType,
        isGroupReader: Boolean,
        openSettings: OpenSettings,
        groupReader: String,
        groupMemberCount: Int,
        groupMember: List<User>
    )

    suspend fun insertAll(entities: List<Category>)

    suspend fun updateCategoryInfo(entity: Category)

    suspend fun deleteCategoryInfo(id: Int)
}