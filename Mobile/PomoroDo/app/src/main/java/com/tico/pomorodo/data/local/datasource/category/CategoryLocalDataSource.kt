package com.tico.pomorodo.data.local.datasource.category

import com.tico.pomorodo.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    suspend fun getAllCategory(): Flow<List<CategoryEntity>>

    suspend fun getCategoryInfo(categoryId: Int): Flow<CategoryEntity>

    suspend fun insert(entity: CategoryEntity)

    suspend fun upsertAll(entities: List<CategoryEntity>)

    suspend fun update(entity: CategoryEntity)

    suspend fun delete(id: Int)
}