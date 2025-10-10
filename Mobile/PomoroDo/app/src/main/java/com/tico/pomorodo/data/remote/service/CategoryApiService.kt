package com.tico.pomorodo.data.remote.service

import com.tico.pomorodo.data.remote.models.request.CategoryDeleteRequest
import com.tico.pomorodo.data.remote.models.request.CategoryInvitationRequest
import com.tico.pomorodo.data.remote.models.request.CategoryRequest
import com.tico.pomorodo.data.remote.models.request.CategoryUpdateRequest
import com.tico.pomorodo.data.remote.models.response.AllCategoryResponse
import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.CategoryResponse
import com.tico.pomorodo.data.remote.models.response.DecideCategoryInvitationResponse
import com.tico.pomorodo.data.remote.models.response.InsertUpdateCategoryResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryApiService {
    @GET("v1/categories")
    suspend fun getAllCategory(): BaseResponse<AllCategoryResponse>

    @GET("v1/categories/{categoryId}")
    suspend fun getCategoryInfo(@Path("categoryId") categoryId: Int): BaseResponse<CategoryResponse>

    @POST("v1/categories")
    suspend fun insertCategory(@Body categoryRequest: CategoryRequest): BaseResponse<InsertUpdateCategoryResponse>

    @PATCH("v1/categories/api/v1/categories/{categoryId}")
    suspend fun updateCategory(
        @Path("categoryId") categoryId: Int,
        @Body categoryUpdateRequest: CategoryUpdateRequest
    ): BaseResponse<InsertUpdateCategoryResponse>

    @HTTP(method = "DELETE", path = "v1/categories/{categoryId}", hasBody = true)
    suspend fun deleteCategory(
        @Path("categoryId") categoryId: Int,
        @Body categoryDeleteRequest: CategoryDeleteRequest
    ): BaseResponse<String>

    @POST("v1/categories/{categoryId}/leave")
    suspend fun outCategory(
        @Path("categoryId") categoryId: Int,
        @Body categoryDeleteRequest: CategoryDeleteRequest
    )

    @PATCH("v1/categories/invitations/{invitationId}")
    suspend fun decideCategoryInvitation(
        @Path("invitationId") invitationId: Int,
        @Body categoryInvitationRequest: CategoryInvitationRequest
    ): BaseResponse<DecideCategoryInvitationResponse>
}