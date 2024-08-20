package com.tico.pomorodo.data.remote.datasource

import com.tico.pomorodo.data.remote.models.response.BaseResponse
import com.tico.pomorodo.data.remote.models.response.TokenResponse
import com.tico.pomorodo.data.remote.service.AuthApiService
import com.tico.pomorodo.domain.model.ProfileImageType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService,
) : AuthDataSource {
    override suspend fun requestLogin(): BaseResponse<TokenResponse> =
        authApiService.requestLogin()

    override suspend fun requestJoin(
        name: String,
        profile: File?,
        profileImageType: ProfileImageType
    ): BaseResponse<TokenResponse> {
        val multipart: MultipartBody.Part = when (profileImageType) {
            ProfileImageType.FILE -> {
                profile?.let {
                    val requestFile =
                        it.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    MultipartBody.Part.createFormData(PROFILE_NAME, it.name, requestFile)
                } ?: MultipartBody.Part.createFormData(PROFILE_NAME, "")
            }

            else -> {
                MultipartBody.Part.createFormData(PROFILE_NAME, "")
            }
        }
        return authApiService.requestJoin(name, multipart, profileImageType)
    }

    companion object {
        const val PROFILE_NAME = "profileImage"
    }
}