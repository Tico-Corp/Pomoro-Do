package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.request.CategoryDeleteRequest
import com.tico.pomorodo.data.remote.models.request.CategoryInvitationRequest
import com.tico.pomorodo.data.remote.models.request.CategoryRequest
import com.tico.pomorodo.data.remote.models.request.CategoryUpdateRequest
import com.tico.pomorodo.data.remote.models.response.AllCategoryResponse
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.CategoryResponse
import com.tico.pomorodo.data.remote.models.response.DecideCategoryInvitationResponse

interface CategoryRemoteDataSource {
    suspend fun getAllCategory(): BaseResponse<AllCategoryResponse>

    suspend fun getCategoryInfo(categoryId: Int): BaseResponse<CategoryResponse>

    suspend fun insertCategory(categoryRequest: CategoryRequest)

    suspend fun updateCategory(categoryId: Int, categoryUpdateRequest: CategoryUpdateRequest)

    suspend fun deleteCategory(categoryId: Int, categoryDeleteRequest: CategoryDeleteRequest)

    suspend fun outCategory(categoryId: Int, categoryDeleteRequest: CategoryDeleteRequest)

    suspend fun decideCategoryInvitation(
        invitationId: Int,
        categoryInvitationRequest: CategoryInvitationRequest
    ): BaseResponse<DecideCategoryInvitationResponse>
}