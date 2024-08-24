package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSource
import com.tico.pomorodo.data.local.entity.toCategory
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.domain.model.Resource
import com.tico.pomorodo.domain.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryLocalDataSource: CategoryLocalDataSource,
    private val networkHelper: NetworkHelper
) : CategoryRepository {
    override suspend fun getAllCategory(): Flow<Resource<List<Category>>> = flow {
        emit(Resource.Loading)

        if (networkHelper.isNetworkConnected()) {
            TODO("bring network data")
        } else {
            emitAll(categoryLocalDataSource.getAllCategory().map {
                wrapToResource(Dispatchers.IO) {
                    it.map { entity -> entity.toCategory() }
                }
            })
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCategory(id: Int): Flow<Resource<Category>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(entity: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun insertAll(entities: List<Category>) {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: Category) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int) {
        TODO("Not yet implemented")
    }
}