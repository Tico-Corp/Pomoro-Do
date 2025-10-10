package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.CategoryDeleteRequest
import com.tico.pomorodo.data.remote.models.request.CategoryInvitationRequest
import com.tico.pomorodo.data.remote.models.request.CategoryRequest
import com.tico.pomorodo.data.remote.models.request.CategoryUpdateRequest
import com.tico.pomorodo.data.remote.models.response.AllCategoryResponse
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.CategoryResponse
import com.tico.pomorodo.data.remote.models.response.DecideCategoryInvitationResponse
import com.tico.pomorodo.data.remote.models.response.InsertUpdateCategoryResponse

interface CategoryRemoteDataSource {
    suspend fun getAllCategory(): BaseResponse<AllCategoryResponse>

    suspend fun getCategoryInfo(categoryId: Int): BaseResponse<CategoryResponse>

    suspend fun insertCategory(categoryRequest: CategoryRequest): BaseResponse<InsertUpdateCategoryResponse>

    suspend fun updateCategory(
        categoryId: Int,
        categoryUpdateRequest: CategoryUpdateRequest
    ): BaseResponse<InsertUpdateCategoryResponse>

    suspend fun deleteCategory(
        categoryId: Int,
        categoryDeleteRequest: CategoryDeleteRequest
    ): BaseResponse<String>

    suspend fun outCategory(categoryId: Int, categoryDeleteRequest: CategoryDeleteRequest)

    suspend fun decideCategoryInvitation(
        invitationId: Int,
        categoryInvitationRequest: CategoryInvitationRequest
    ): BaseResponse<DecideCategoryInvitationResponse>
}