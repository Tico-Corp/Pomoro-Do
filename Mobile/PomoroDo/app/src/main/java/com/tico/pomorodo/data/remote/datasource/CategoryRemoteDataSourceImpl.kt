package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.CategoryDeleteRequest
import com.tico.pomorodo.data.remote.models.request.CategoryInvitationRequest
import com.tico.pomorodo.data.remote.models.request.CategoryRequest
import com.tico.pomorodo.data.remote.models.request.CategoryUpdateRequest
import com.tico.pomorodo.data.remote.models.response.AllCategoryResponse
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.CategoryResponse
import com.tico.pomorodo.data.remote.models.response.DecideCategoryInvitationResponse
import com.tico.pomorodo.data.remote.service.CategoryApiService
import javax.inject.Inject

class CategoryRemoteDataSourceImpl @Inject constructor(val categoryApiService: CategoryApiService) :
    CategoryRemoteDataSource {
    override suspend fun getAllCategory(): BaseResponse<AllCategoryResponse> =
        categoryApiService.getAllCategory()

    override suspend fun getCategoryInfo(categoryId: Int): BaseResponse<CategoryResponse> =
        categoryApiService.getCategoryInfo(categoryId)

    override suspend fun insertCategory(categoryRequest: CategoryRequest) {
        categoryApiService.insertCategory(categoryRequest)
    }

    override suspend fun updateCategory(
        categoryId: Int,
        categoryUpdateRequest: CategoryUpdateRequest
    ) {
        categoryApiService.updateCategory(categoryId, categoryUpdateRequest)
    }

    override suspend fun deleteCategory(
        categoryId: Int,
        categoryDeleteRequest: CategoryDeleteRequest
    ) {
        categoryApiService.deleteCategory(categoryId, categoryDeleteRequest)
    }

    override suspend fun outCategory(
        categoryId: Int,
        categoryDeleteRequest: CategoryDeleteRequest
    ) {
        categoryApiService.outCategory(categoryId, categoryDeleteRequest)
    }

    override suspend fun decideCategoryInvitation(
        invitationId: Int,
        categoryInvitationRequest: CategoryInvitationRequest
    ): BaseResponse<DecideCategoryInvitationResponse> {
        return categoryApiService.decideCategoryInvitation(invitationId, categoryInvitationRequest)
    }
}