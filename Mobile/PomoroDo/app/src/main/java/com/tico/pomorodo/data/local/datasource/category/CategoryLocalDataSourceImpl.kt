package com.tico.pomorodo.data.local.datasource.category

import com.tico.pomorodo.data.local.dao.CategoryDao
import com.tico.pomorodo.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryLocalDataSourceImpl @Inject constructor(private val categoryDao: CategoryDao) :
    CategoryLocalDataSource {
    override suspend fun getAllCategory(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategory()
    }

    override suspend fun getCategory(id: Int): Flow<CategoryEntity> {
        return categoryDao.getCategory(id)
    }

    override suspend fun insert(entity: CategoryEntity) {
        return categoryDao.insert(entity)
    }

    override suspend fun insertAll(entities: List<CategoryEntity>) {
        return categoryDao.insertAll(entities)
    }

    override suspend fun update(entity: CategoryEntity) {
        return categoryDao.update(entity)
    }

    override suspend fun delete(id: Int) {
        return categoryDao.delete(id)
    }
}