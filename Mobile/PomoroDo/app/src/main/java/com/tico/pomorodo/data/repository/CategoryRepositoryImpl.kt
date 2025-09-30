package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSource
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.toCategory
import com.tico.pomorodo.data.local.entity.toGroupCategory
import com.tico.pomorodo.data.local.entity.toPersonalCategory
import com.tico.pomorodo.data.model.AllCategory
import com.tico.pomorodo.data.model.Category
import com.tico.pomorodo.data.model.CategoryType
import com.tico.pomorodo.data.model.Decision
import com.tico.pomorodo.data.model.DeletionOption
import com.tico.pomorodo.data.model.OpenSettings
import com.tico.pomorodo.data.model.toCategoryEntity
import com.tico.pomorodo.data.model.toCategoryUpdateRequest
import com.tico.pomorodo.data.remote.datasource.CategoryRemoteDataSource
import com.tico.pomorodo.data.remote.models.request.CategoryDeleteRequest
import com.tico.pomorodo.data.remote.models.request.CategoryInvitationRequest
import com.tico.pomorodo.data.remote.models.request.CategoryRequest
import com.tico.pomorodo.data.remote.models.response.toAllCategory
import com.tico.pomorodo.data.remote.models.response.toCategory
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
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val networkHelper: NetworkHelper
) : CategoryRepository {
    override suspend fun getAllCategory(): Flow<Resource<AllCategory>> = flow {
        emit(Resource.Loading)

        if (networkHelper.isNetworkConnected()) {
            val data = wrapToResource(Dispatchers.IO) {
                categoryRemoteDataSource.getAllCategory().data.toAllCategory()
            }
            emit(data)
        } else {
            val res = categoryLocalDataSource.getAllCategory().map {
                wrapToResource(Dispatchers.IO) {
                    it.partition { it.type == CategoryType.PERSONAL }.let { (personal, group) ->
                        AllCategory(
                            personalCategories = personal.map(CategoryEntity::toPersonalCategory),
                            groupCategories = group.map(CategoryEntity::toGroupCategory)
                        )
                    }
                }
            }
            emitAll(res)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getCategoryInfo(categoryId: Int): Flow<Resource<Category>> = flow {
        emit(Resource.Loading)

        if (networkHelper.isNetworkConnected()) {
            val res = categoryRemoteDataSource.getCategoryInfo(categoryId).data.toCategory()
            emit(wrapToResource(Dispatchers.IO) { res })
        } else {
            val data = categoryLocalDataSource.getCategoryInfo(categoryId).map {
                wrapToResource(Dispatchers.IO) {
                    it.toCategory()
                }
            }
            emitAll(data)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun insertCategory(
        title: String,
        type: CategoryType,
        openSettings: OpenSettings,
        groupMemberIds: List<Int>?,
        startDate: String
    ) {
        if (networkHelper.isNetworkConnected()) {
            categoryRemoteDataSource.insertCategory(
                CategoryRequest(
                    startDate = startDate,
                    title = title,
                    type = type,
                    openSettings = openSettings,
                    memberIds = groupMemberIds
                )
            )
            val categoryEntity = CategoryEntity(
                title = title,
                type = type,
                openSettings = openSettings,
            )
            categoryLocalDataSource.insert(categoryEntity)
        } else {
            val categoryEntity = CategoryEntity(
                title = title,
                type = type,
                openSettings = openSettings,
            )
            categoryLocalDataSource.insert(categoryEntity)
        }
    }

    override suspend fun updateCategory(category: Category) {
        if (networkHelper.isNetworkConnected()) {
            categoryRemoteDataSource.updateCategory(
                category.id,
                category.toCategoryUpdateRequest()
            )
        } else {
            categoryLocalDataSource.update(category.toCategoryEntity())
        }
    }

    override suspend fun deleteCategory(categoryId: Int, deleteOption: DeletionOption) {
        if (networkHelper.isNetworkConnected()) {
            categoryRemoteDataSource.deleteCategory(
                categoryId,
                CategoryDeleteRequest(deleteOption)
            )
        } else {
            categoryLocalDataSource.delete(categoryId)
        }
    }

    override suspend fun outCategory(
        categoryId: Int,
        deleteOption: DeletionOption
    ) {
        if (networkHelper.isNetworkConnected()) {
            categoryRemoteDataSource.outCategory(
                categoryId,
                CategoryDeleteRequest(deleteOption)
            )
        }
    }

    override suspend fun decideCategoryInvitation(
        invitationId: Int,
        decision: Decision
    ) {
        if (networkHelper.isNetworkConnected()) {
            categoryRemoteDataSource.decideCategoryInvitation(
                invitationId,
                CategoryInvitationRequest(decision)
            )
        }
    }
}