package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSource
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.toCategory
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.User
import com.tico.pomorodo.data.model.toCategoryEntity
import com.tico.pomorodo.data.model.toUserEntity
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
            // TODO:bring network data
        } else {
            emitAll(categoryLocalDataSource.getAllCategory().map {
                wrapToResource(Dispatchers.IO) {
                    it.map { entity -> entity.toCategory() }
                }
            })
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCategoryInfo(categoryId: Int): Flow<Resource<Category>> = flow {
        emit(Resource.Loading)

        if (networkHelper.isNetworkConnected()) {
            // TODO:bring network data
        } else {
            val data = categoryLocalDataSource.getCategoryInfo(categoryId).map {
                wrapToResource(Dispatchers.IO) {
                    it.toCategory()
                }
            }
            emitAll(data)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insert(
        title: String,
        type: CategoryType,
        isGroupReader: Boolean,
        openSettings: OpenSettings,
        groupReader: String,
        groupMemberCount: Int,
        groupMember: List<User>
    ) {
        val categoryEntity = CategoryEntity(
            title = title,
            type = type,
            isGroupReader = isGroupReader,
            openSettings = openSettings,
            groupReader = groupReader,
            groupMemberCount = groupMemberCount,
            groupMember = groupMember.map(User::toUserEntity)
        )
        if (networkHelper.isNetworkConnected()) {

        } else {
            categoryLocalDataSource.insert(categoryEntity)
        }
    }

    override suspend fun insertAll(entities: List<Category>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCategoryInfo(entity: Category) {
        if (networkHelper.isNetworkConnected()) {

        } else {
            categoryLocalDataSource.update(entity.toCategoryEntity())
        }
    }

    override suspend fun deleteCategoryInfo(id: Int) {
        if (networkHelper.isNetworkConnected()) {

        } else {
            categoryLocalDataSource.delete(id)
        }
    }
}