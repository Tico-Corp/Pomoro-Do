package com.tico.pomorodo.domain.repository

import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.domain.model.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategory(): Flow<Resource<List<Category>>>

    suspend fun getCategory(id: Int): Flow<Resource<Category>>

    suspend fun insert(entity: Category)

    suspend fun insertAll(entities: List<Category>)

    suspend fun update(entity: Category)

    suspend fun delete(id: Int)
}