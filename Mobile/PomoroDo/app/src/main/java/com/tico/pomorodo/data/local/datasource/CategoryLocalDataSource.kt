package com.tico.pomorodo.data.local.datasource

import com.tico.pomorodo.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CategoryLocalDataSource {
    suspend fun getAllCategory(): Flow<List<CategoryEntity>>

    suspend fun getCategory(id: Int): Flow<CategoryEntity>

    suspend fun insert(entity: CategoryEntity)

    suspend fun insertAll(entities: List<CategoryEntity>)

    suspend fun update(entity: CategoryEntity)

    suspend fun delete(id: Int)
}