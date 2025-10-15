package com.tico.pomorodo.data.repository

import com.tico.pomorodo.common.util.NetworkConstants
import com.tico.pomorodo.common.util.NetworkHelper
import com.tico.pomorodo.common.util.wrapToResource
import com.tico.pomorodo.data.local.datasource.category.CategoryLocalDataSource
import com.tico.pomorodo.data.local.entity.CategoryEntity
import com.tico.pomorodo.data.local.entity.SyncState
import com.tico.pomorodo.data.local.entity.toCategory
import com.tico.pomorodo.data.local.entity.toCategoryList
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
import com.tico.pomorodo.data.remote.models.response.toCategoryEntity
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
                val res = categoryRemoteDataSource.getAllCategory().data.toAllCategory()
                categoryLocalDataSource.upsertAll(res.toCategoryEntity())
                res
            }
            emit(data)
        } else {
            val res = categoryLocalDataSource.getAllCategory().map {
                wrapToResource(Dispatchers.IO) {
                    it.partition { it.type == CategoryType.PERSONAL }.let { (personal, group) ->
                        AllCategory(
                            personalCategories = personal.map(CategoryEntity::toCategoryList),
                            groupCategories = group.map(CategoryEntity::toCategoryList)
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
            val data = wrapToResource(Dispatchers.IO) {
                val response = categoryRemoteDataSource.getCategoryInfo(categoryId)
                val category = response.data.toCategory()
                categoryLocalDataSource.update(category.toCategoryEntity())
                category
            }
            emit(data)
        } else {
            val data = categoryLocalDataSource.getCategoryInfo(categoryId)
                .map { wrapToResource(Dispatchers.IO) { it.toCategory() } }
            emitAll(data)
        }
    }

    override suspend fun insertCategory(
        title: String,
        type: CategoryType,
        openSettings: OpenSettings,
        groupMemberIds: List<Int>?,
        startDate: String
    ) {
        if (networkHelper.isNetworkConnected()) {
            val res = categoryRemoteDataSource.insertCategory(
                CategoryRequest(
                    startDate = startDate,
                    title = title,
                    type = type,
                    openSettings = openSettings,
                    memberIds = groupMemberIds
                )
            )
            categoryLocalDataSource.insert(res.data.toCategoryEntity())
        } else {
            val categoryEntity = CategoryEntity(
                title = title,
                type = type,
                openSettings = openSettings,
                syncState = SyncState.PENDING_CREATE
            )
            categoryLocalDataSource.insert(categoryEntity)
        }
    }

    override suspend fun updateCategory(category: Category) {
        if (networkHelper.isNetworkConnected()) {
            val res = categoryRemoteDataSource.updateCategory(
                category.id,
                category.toCategoryUpdateRequest()
            )

            val updatedEntity = res.data.toCategoryEntity()
            categoryLocalDataSource.update(updatedEntity)
        } else {
            categoryLocalDataSource.update(
                category.toCategoryEntity(SyncState.PENDING_UPDATE)
            )
        }
    }

    override suspend fun deleteCategory(categoryId: Int, deleteOption: DeletionOption) {
        if (networkHelper.isNetworkConnected()) {
            val res = categoryRemoteDataSource.deleteCategory(
                categoryId,
                CategoryDeleteRequest(deleteOption)
            )
            if (res.code == NetworkConstants.SUCCESS) {
                categoryLocalDataSource.delete(categoryId)
            }
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
    ): Resource<Unit> {
        return wrapToResource(Dispatchers.IO) {
            val res = categoryRemoteDataSource.decideCategoryInvitation(
                invitationId,
                CategoryInvitationRequest(decision)
            )
            categoryLocalDataSource.update(
                res.data.toCategoryEntity(CategoryType.GROUP, OpenSettings.GROUP)
            )
        }
    }
}